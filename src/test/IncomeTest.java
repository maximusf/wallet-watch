// Copyright 2024 maximusf
// Income Test
package test;

import models.Income;
import dao.IncomeDAO;
import util.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Tests all Income-related database operations
 * This ensures our database functions work correctly
 */
public class IncomeTest {
    // Get database settings from .env file
    private static final String URL = Environment.get("DB_URL");
    private static final String USER = Environment.get("DB_USER");
    private static final String PASS = Environment.get("DB_PASS");

    public static void main(String[] args) {
        try {
            // Connect to database and run all tests
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            IncomeDAO dao = new IncomeDAO(conn);
            
            cleanup(dao);                  // Clear any old test data
            testDatabaseOperations(dao);   // Test basic operations
            testEdgeCases(dao);           // Test unusual cases
            cleanup(dao);                  // Clean up after tests
            
            System.out.println("All tests passed!");
            conn.close();
            
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Remove all test data from database
    private static void cleanup(IncomeDAO dao) throws SQLException {
        List<Income> incomes = dao.getIncomeByUserId(1);
        for (Income income : incomes) {
            dao.deleteIncome(income.getId());
        }
    }

    // Test adding, reading, and updating income records
    private static void testDatabaseOperations(IncomeDAO dao) throws SQLException {
        System.out.println("\nTesting basic database operations...");

        // Add new income and verify it was saved
        Income testIncome = new Income(0, 1, 999.99, "Test Income", "2024-03-15");
        dao.addIncome(testIncome);
        assert testIncome.getId() > 0 : "Income ID should be set after insertion";

        // Find the income we just added
        List<Income> incomes = dao.getIncomeByUserId(1);
        Income added = incomes.stream()
            .filter(i -> i.getSource().equals("Test Income"))
            .findFirst()
            .orElseThrow(() -> new AssertionError("Added income not found"));

        // Verify all fields match what we saved
        assert added.getAmount() == 999.99 : "Amount should match";
        assert added.getSource().equals("Test Income") : "Source should match";
        assert added.getDate().equals("2024-03-15") : "Date should match";

        // Test updating an existing record
        added.setAmount(1000.00);
        dao.updateIncome(added);
        incomes = dao.getIncomeByUserId(1);
        Income lastIncome = incomes.get(incomes.size() - 1);
        assert lastIncome.getAmount() == 1000.00 : "Updated amount should match";

        System.out.println("Basic operations tests passed!");
    }

    // Test unusual or extreme cases
    private static void testEdgeCases(IncomeDAO dao) throws SQLException {
        System.out.println("\nTesting edge cases...");

        // Try to find income for a user that doesn't exist
        List<Income> noIncomes = dao.getIncomeByUserId(999999);
        assert noIncomes.isEmpty() : "Non-existent user should return empty list";

        try {
            // Test with a very large money amount
            Income largeIncome = new Income(0, 1, 999999.99, "Large Test", "2024-03-15");
            dao.addIncome(largeIncome);
            dao.deleteIncome(largeIncome.getId());
            System.out.println("Large value test passed");
        } catch (SQLException e) {
            throw new AssertionError("Failed to handle large value", e);
        }

        try {
            // Test with special characters in the description
            Income specialChars = new Income(0, 1, 100.00, "Test!@#$%^&*()", "2024-03-15");
            dao.addIncome(specialChars);
            dao.deleteIncome(specialChars.getId());
            System.out.println("Special characters test passed");
        } catch (SQLException e) {
            throw new AssertionError("Failed to handle special characters", e);
        }

        System.out.println("Edge cases tests passed!");
    }
}