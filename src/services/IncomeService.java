// IncomeService.java
// Copyright 2024 maximusf

// This file contains the IncomeService class, which handles all income-related operations.
// It includes methods for adding, viewing, updating, and deleting income records, as well as calculating total income.
// It also includes methods for validating and formatting dates.

package services;

import dao.IncomeDAO;
import models.Income;
import util.PrintBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Service class to handle all Income-related operations
 */
public class IncomeService {
    private final IncomeDAO dao;
    private final Scanner scanner;
    private final int currentUserId;
    private final boolean isAdmin;

    /**
     * Constructs a new IncomeService
     * @param dao Data access object for incomes
     * @param scanner Scanner for user input
     * @param currentUserId ID of the current user
     * @param isAdmin Whether the current user is an admin
     */
    public IncomeService(IncomeDAO dao, Scanner scanner, int currentUserId, boolean isAdmin) {
        this.dao = dao;
        this.scanner = scanner;
        this.currentUserId = currentUserId;
        this.isAdmin = isAdmin;
    }

    /**
     * Adds a new income record
     */
    public void addIncome() {
        while (true) {
            try {
                PrintBuilder.message("Enter Amount: ").asPrompt().print();
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                
                PrintBuilder.message("Enter Source: ").asPrompt().print();
                String source = scanner.nextLine();
                
                String date = getValidDate();

                Income income = new Income(0, currentUserId, amount, source, date);
                dao.add(income);
                PrintBuilder.message("Income added successfully!").asSuccess().print();
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    /**
     * Views incomes for current user or specified user if admin
     */
    public void viewIncome() throws SQLException {
        if (isAdmin) {
            PrintBuilder.message("Enter User ID to view (or 0 for all users): ").asPrompt().print();
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            List<Income> incomes = targetUserId == 0 ? dao.getAllIncomes() : dao.getByUserId(targetUserId);
            displayIncomes(incomes, targetUserId);
        } else {
            List<Income> incomes = dao.getByUserId(currentUserId);
            displayIncomes(incomes, currentUserId);
        }
    }

    /**
     * Updates an existing income record (admin only)
     */
    public void updateIncome() {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }
        
        while (true) {
            try {
                PrintBuilder.message("Enter User ID: ").asPrompt().print();
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                PrintBuilder.message("Enter Income ID to update: ").asPrompt().print();
                int updateId = scanner.nextInt();
                scanner.nextLine();
                
                PrintBuilder.message("Enter new Amount: ").asPrompt().print();
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                
                PrintBuilder.message("Enter new Source: ").asPrompt().print();
                String newSource = scanner.nextLine();
                
                String newDate = getValidDate();

                Income updatedIncome = new Income(updateId, targetUserId, newAmount, newSource, newDate);
                dao.update(updatedIncome);
                PrintBuilder.message("Income updated successfully!").asSuccess().print();
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine();
            }
        }
    }

    /**
     * Deletes an income record (admin only)
     */
    public void deleteIncome() {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }

        while (true) {
            try {
                PrintBuilder.message("Enter User ID: ").asPrompt().print();
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                List<Income> incomes = dao.getByUserId(targetUserId);
                if (incomes.isEmpty()) {
                    PrintBuilder.message("No income records found for User ID: " + targetUserId).print();
                    break;
                }
                
                PrintBuilder.message("Enter Income ID to delete: ").asPrompt().print();
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                
                if (dao.deleteById(deleteId)) {
                    PrintBuilder.message("Income deleted successfully!").asSuccess().print();
                } else {
                    PrintBuilder.message("Income not found.").asError().print();
                }
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine();
            }
        }
    }

    /**
     * Gets total income for a user or all users if admin
     */
    public void getTotalIncome() throws SQLException {
        if (isAdmin) {
            PrintBuilder.message("Enter User ID (or 0 for all users): ").asPrompt().print();
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            
            List<Income> incomes = targetUserId == 0 ? 
                dao.getAllIncomes() : 
                dao.getByUserId(targetUserId);
            
            double total = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();
            
            PrintBuilder.message(String.format(
                "Total Income%s: $%.2f", 
                targetUserId == 0 ? "" : " for User " + targetUserId,
                total
            )).print();
        } else {
            List<Income> incomes = dao.getByUserId(currentUserId);
            double total = incomes.stream()
                .mapToDouble(Income::getAmount)
                .sum();
            PrintBuilder.message(String.format(
                "Your Total Income: $%.2f", 
                total
            )).print();
        }
    }

    /**
     * Displays a list of incomes
     */
    private void displayIncomes(List<Income> incomes, int userId) {
        if (incomes.isEmpty()) {
            PrintBuilder.message("No income records found" + 
                (userId != 0 ? " for User ID: " + userId : "")).print();
        } else {
            PrintBuilder.message("\nIncome Records" + 
                (userId != 0 ? " for User ID: " + userId : "")).print();
            for (Income income : incomes) {
                PrintBuilder.message(income.toString()).print();
            }
        }
    }

    /**
     * Gets a valid date from user input
     */
    private String getValidDate() {
        while (true) {
            try {
                PrintBuilder.message("Enter Date (YYYYMMDD): ").asPrompt().print();
                return formatDate(scanner.nextLine());
            } catch (IllegalArgumentException e) {
                PrintBuilder.message(e.getMessage()).asError().print();
            }
        }
    }

    /**
     * Formats and validates a date string
     */
    private String formatDate(String input) {
        // Remove any existing dashes
        String cleaned = input.replaceAll("-", "");
        if (!cleaned.matches("\\d{8}")) {
            throw new IllegalArgumentException("Date must be in YYYYMMDD format");
        }

        // Extract parts
        int year = Integer.parseInt(cleaned.substring(0, 4));
        int month = Integer.parseInt(cleaned.substring(4, 6));
        int day = Integer.parseInt(cleaned.substring(6, 8));

        // Validate date parts
        if (year < 1900 || year > 2100 ||
            month < 1 || month > 12 ||
            day < 1 || day > 31) {
            throw new IllegalArgumentException("Invalid date. Please use a valid date in YYYYMMDD format");
        }

        return String.format("%d-%02d-%02d", year, month, day);
    }

    /**
     * Deletes all income records (admin only)
     */
    public void deleteAllIncomes() throws SQLException {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }
        List<Income> incomes = dao.getAllIncomes();
        for (Income income : incomes) {
            dao.deleteById(income.getId());
        }
    }
}
