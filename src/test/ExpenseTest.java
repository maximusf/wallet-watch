// ExpenseTest.java
// Copyright 2024 maximusf

package test;

import models.Expense;
import dao.ExpenseDAO;
import util.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Tests all Expense-related database operations
 * This ensures our database functions work correctly
 */
public class ExpenseTest {
    private static final String URL = Environment.get("DB_URL");
    private static final String USER = Environment.get("DB_USER");
    private static final String PASS = Environment.get("DB_PASS");

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            ExpenseDAO dao = new ExpenseDAO(conn);
            
            cleanup(dao);
            testDatabaseOperations(dao);
            testEdgeCases(dao);
            cleanup(dao);
            
            System.out.println("All expense tests passed!");
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private static void cleanup(ExpenseDAO dao) throws SQLException {
        List<Expense> expenses = dao.getByUserId(1);
        for (Expense expense : expenses) {
            dao.deleteById(expense.getId());
        }
    }

    private static void testDatabaseOperations(ExpenseDAO dao) throws SQLException {
        System.out.println("\nTesting basic expense operations...");

        // Add new expense
        Expense testExpense = new Expense(0, 1, 50.99, "Groceries", "2024-03-15");
        dao.add(testExpense);
        assert testExpense.getId() > 0 : "Expense ID should be set after insertion";

        // Find the expense
        List<Expense> expenses = dao.getByUserId(1);
        Expense added = expenses.stream()
            .filter(e -> e.getCategory().equals("Groceries"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Added expense not found"));

        // Verify fields
        assert added.getAmount() == 50.99 : "Amount should match";
        assert added.getCategory().equals("Groceries") : "Category should match";
        assert added.getDate().equals("2024-03-15") : "Date should match";

        // Test update
        added.setAmount(75.99);
        dao.update(added);
        expenses = dao.getByUserId(1);
        Expense updated = expenses.get(expenses.size() - 1);
        assert updated.getAmount() == 75.99 : "Updated amount should match";

        System.out.println("Basic expense operations passed!");
    }

    private static void testEdgeCases(ExpenseDAO dao) throws SQLException {
        System.out.println("\nTesting expense edge cases...");

        // Test non-existent user
        List<Expense> noExpenses = dao.getByUserId(999999);
        assert noExpenses.isEmpty() : "Non-existent user should return empty list";

        try {
            // Test large amount
            Expense largeExpense = new Expense(0, 1, 9999.99, "Large Test", "2024-03-15");
            dao.add(largeExpense);
            dao.deleteById(largeExpense.getId());
            System.out.println("Large expense value test passed");
        } catch (SQLException e) {
            throw new AssertionError("Failed to handle large expense value", e);
        }

        try {
            // Test special characters
            Expense specialChars = new Expense(0, 1, 100.00, "Test!@#$%^&*()", "2024-03-15");
            dao.add(specialChars);
            dao.deleteById(specialChars.getId());
            System.out.println("Special characters in expense test passed");
        } catch (SQLException e) {
            throw new AssertionError("Failed to handle special characters in expense", e);
        }

        System.out.println("Expense edge cases passed!");
    }
}
