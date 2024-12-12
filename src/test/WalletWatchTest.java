package test;

import models.Income;
import models.Expense;
import dao.IncomeDAO;
import dao.ExpenseDAO;
import util.Environment;
import util.PrintBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Tests all database operations for both Income and Expense
 */
public class WalletWatchTest {
    private static final String URL = Environment.get("DB_URL");
    private static final String USER = Environment.get("DB_USER");
    private static final String PASS = Environment.get("DB_PASS");

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASS);
            IncomeDAO incomeDao = new IncomeDAO(conn);
            ExpenseDAO expenseDao = new ExpenseDAO(conn);
            
            cleanup(incomeDao, expenseDao);
            testIncomeOperations(incomeDao);
            testExpenseOperations(expenseDao);
            testEdgeCases(incomeDao, expenseDao);
            cleanup(incomeDao, expenseDao);
            
            PrintBuilder.message("All tests passed!").asSuccess().print();
            conn.close();
        } catch (SQLException e) {
            PrintBuilder.message("Database error: " + e.getMessage()).asError().print();
        }
    }

    private static void cleanup(IncomeDAO incomeDao, ExpenseDAO expenseDao) throws SQLException {
        // Clean up expenses first due to foreign key constraints
        List<Expense> expenses = expenseDao.getByUserId(1);
        for (Expense expense : expenses) {
            expenseDao.deleteById(expense.getId());
        }
        
        List<Income> incomes = incomeDao.getByUserId(1);
        for (Income income : incomes) {
            incomeDao.deleteById(income.getId());
        }
    }

    private static void testIncomeOperations(IncomeDAO dao) throws SQLException {
        PrintBuilder.message("\nTesting income operations...").print();
        
        Income testIncome = new Income(0, 1, 999.99, "Test Income", "2024-03-15");
        dao.add(testIncome);
        
        if (testIncome.getId() <= 0) {
            throw new SQLException("Income ID not set after insertion");
        }

        List<Income> incomes = dao.getByUserId(1);
        Optional<Income> foundIncome = findIncomeBySource(incomes, "Test Income");
        if (!foundIncome.isPresent()) {
            throw new SQLException("Added income not found");
        }

        Income added = foundIncome.get();
        if (added.getAmount() != 999.99) {
            throw new SQLException("Income amount doesn't match");
        }
        if (!added.getSource().equals("Test Income")) {
            throw new SQLException("Income source doesn't match");
        }
        
        PrintBuilder.message("Income operations passed!").asSuccess().print();
    }

    private static void testExpenseOperations(ExpenseDAO dao) throws SQLException {
        PrintBuilder.message("\nTesting expense operations...").print();
        
        Expense testExpense = new Expense(0, 1, 50.99, "Test Expense", "2024-03-15");
        dao.add(testExpense);
        
        if (testExpense.getId() <= 0) {
            throw new SQLException("Expense ID not set after insertion");
        }

        List<Expense> expenses = dao.getByUserId(1);
        Optional<Expense> foundExpense = findExpenseByCategory(expenses, "Test Expense");
        if (!foundExpense.isPresent()) {
            throw new SQLException("Added expense not found");
        }

        Expense added = foundExpense.get();
        if (added.getAmount() != 50.99) {
            throw new SQLException("Expense amount doesn't match");
        }
        if (!added.getCategory().equals("Test Expense")) {
            throw new SQLException("Expense category doesn't match");
        }
        
        PrintBuilder.message("Expense operations passed!").asSuccess().print();
    }

    private static void testEdgeCases(IncomeDAO incomeDao, ExpenseDAO expenseDao) throws SQLException {
        PrintBuilder.message("\nTesting edge cases...").print();

        // Test non-existent user
        if (!incomeDao.getByUserId(999999).isEmpty()) {
            throw new SQLException("Non-existent user should return empty income list");
        }
        if (!expenseDao.getByUserId(999999).isEmpty()) {
            throw new SQLException("Non-existent user should return empty expense list");
        }

        // Test large values
        Income largeIncome = new Income(0, 1, 999999.99, "Large Test", "2024-03-15");
        incomeDao.add(largeIncome);
        incomeDao.deleteById(largeIncome.getId());
        PrintBuilder.message("Large value test passed").asSuccess().print();

        // Test special characters
        Expense specialExpense = new Expense(0, 1, 100.00, "Test!@#$%^&*()", "2024-03-15");
        expenseDao.add(specialExpense);
        expenseDao.deleteById(specialExpense.getId());
        PrintBuilder.message("Special characters test passed").asSuccess().print();

        PrintBuilder.message("Edge cases passed!").asSuccess().print();
    }

    private static Optional<Income> findIncomeBySource(List<Income> incomes, String source) {
        for (Income income : incomes) {
            if (income.getSource().equals(source)) {
                return Optional.of(income);
            }
        }
        return Optional.empty();
    }

    private static Optional<Expense> findExpenseByCategory(List<Expense> expenses, String category) {
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
                return Optional.of(expense);
            }
        }
        return Optional.empty();
    }
}
