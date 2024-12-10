// Copyright 2024 maximusf
// Income Test
package test;

import models.Income;
import dao.IncomeDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class IncomeTest {
    // Track test records for cleanup
    private static List<Integer> testIncomeIds = new ArrayList<>();

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/budget_tracker",
            "root",
            "your_password"
        );
    }

    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            IncomeDAO dao = new IncomeDAO(conn);
            
            try {
                // Run all tests
                testInputValidation();
                testDatabaseOperations(dao);
                testEdgeCases(dao);
                
                System.out.println("\nAll tests completed!");
            } finally {
                // Cleanup test data
                cleanup(dao);
            }
            
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static void cleanup(IncomeDAO dao) throws SQLException {
        System.out.println("\nCleaning up test data...");
        for (Integer id : testIncomeIds) {
            try {
                dao.deleteIncome(id);
                System.out.println("✓ Deleted test income ID: " + id);
            } catch (SQLException e) {
                System.out.println("❌ Failed to delete test income ID: " + id);
            }
        }
        testIncomeIds.clear();
    }

    private static void testInputValidation() {
        System.out.println("\nTesting Input Validation:");
        
        try {
            // Test negative amount
            new Income(1, 1, -100.00, "Test", "2024-03-15");
            System.out.println("❌ Failed: Negative amount accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Passed: Negative amount rejected");
        }

        try {
            // Test empty source
            new Income(1, 1, 100.00, "", "2024-03-15");
            System.out.println("❌ Failed: Empty source accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Passed: Empty source rejected");
        }

        try {
            // Test invalid date format
            new Income(1, 1, 100.00, "Test", "2024/03/15");
            System.out.println("❌ Failed: Invalid date format accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("✓ Passed: Invalid date format rejected");
        }
    }

    private static void testDatabaseOperations(IncomeDAO dao) throws SQLException {
        System.out.println("\nTesting Database Operations:");
        
        // Add test record
        Income testIncome = new Income(0, 1, 999.99, "Test Income", "2024-03-15");
        dao.addIncome(testIncome);
        
        // Store ID for cleanup
        List<Income> incomes = dao.getIncomeByUserId(1);
        Income added = incomes.stream()
            .filter(i -> i.getAmount() == 999.99 && "Test Income".equals(i.getSource()))
            .findFirst()
            .orElse(null);
            
        if (added != null) {
            testIncomeIds.add(added.getId());
        }

        // Test Update
        Income lastIncome = incomes.get(incomes.size() - 1);
        lastIncome.setAmount(1000.00);
        dao.updateIncome(lastIncome);
        
        // Test Delete
        dao.deleteIncome(lastIncome.getId());
    }

    private static void testEdgeCases(IncomeDAO dao) throws SQLException {
        System.out.println("\nTesting Edge Cases:");
        
        // Test non-existent user
        List<Income> noIncomes = dao.getIncomeByUserId(999999);
        System.out.println(noIncomes.isEmpty() ? 
            "✓ Passed: No incomes for non-existent user" : 
            "❌ Failed: Found incomes for non-existent user");

        // Test maximum values
        try {
            Income maxIncome = new Income(0, 1, Double.MAX_VALUE, "Max Test", "2024-03-15");
            dao.addIncome(maxIncome);
            System.out.println("✓ Passed: Handled maximum amount");
        } catch (Exception e) {
            System.out.println("❌ Failed: Could not handle maximum amount");
        }

        // Test special characters in source
        try {
            Income specialChars = new Income(0, 1, 100.00, "Test!@#$%^&*()", "2024-03-15");
            dao.addIncome(specialChars);
            System.out.println("✓ Passed: Handled special characters");
        } catch (Exception e) {
            System.out.println("❌ Failed: Could not handle special characters");
        }
    }
}