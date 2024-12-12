package services;

import dao.ExpenseDAO;
import models.Expense;
import util.PrintBuilder;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Service class to handle all Expense-related operations
 */
public class ExpenseService {
    private final ExpenseDAO dao;
    private final Scanner scanner;
    private final int currentUserId;
    private final boolean isAdmin;

    /**
     * Constructs a new ExpenseService
     * @param dao Data access object for expenses
     * @param scanner Scanner for user input
     * @param currentUserId ID of the current user
     * @param isAdmin Whether the current user is an admin
     */
    public ExpenseService(ExpenseDAO dao, Scanner scanner, int currentUserId, boolean isAdmin) {
        this.dao = dao;
        this.scanner = scanner;
        this.currentUserId = currentUserId;
        this.isAdmin = isAdmin;
    }

    /**
     * Adds a new expense record
     */
    public void addExpense() {
        while (true) {
            try {
                PrintBuilder.message("Enter Amount: ").asPrompt().print();
                double amount = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                
                PrintBuilder.message("Enter Category: ").asPrompt().print();
                String category = scanner.nextLine();
                
                String date = getValidDate();

                Expense expense = new Expense(0, currentUserId, amount, category, date);
                dao.add(expense);
                PrintBuilder.message("Expense added successfully!").asSuccess().print();
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    /**
     * Views expenses for current user or specified user if admin
     */
    public void viewExpenses() throws SQLException {
        if (isAdmin) {
            PrintBuilder.message("Enter User ID to view (or 0 for all users): ").asPrompt().print();
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            List<Expense> expenses = targetUserId == 0 ? dao.getAllExpenses() : dao.getByUserId(targetUserId);
            displayExpenses(expenses, targetUserId);
        } else {
            List<Expense> expenses = dao.getByUserId(currentUserId);
            displayExpenses(expenses, currentUserId);
        }
    }

    /**
     * Updates an existing expense record (admin only)
     */
    public void updateExpense() {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }
        
        while (true) {
            try {
                PrintBuilder.message("Enter User ID: ").asPrompt().print();
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                PrintBuilder.message("Enter Expense ID to update: ").asPrompt().print();
                int updateId = scanner.nextInt();
                scanner.nextLine();
                
                PrintBuilder.message("Enter new Amount: ").asPrompt().print();
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                
                PrintBuilder.message("Enter new Category: ").asPrompt().print();
                String newCategory = scanner.nextLine();
                
                String newDate = getValidDate();

                Expense updatedExpense = new Expense(updateId, targetUserId, newAmount, newCategory, newDate);
                dao.update(updatedExpense);
                PrintBuilder.message("Expense updated successfully!").asSuccess().print();
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine();
            }
        }
    }

    /**
     * Deletes an expense record (admin only)
     */
    public void deleteExpense() {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }

        while (true) {
            try {
                PrintBuilder.message("Enter User ID: ").asPrompt().print();
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                List<Expense> expenses = dao.getByUserId(targetUserId);
                if (expenses.isEmpty()) {
                    PrintBuilder.message("No expense records found for User ID: " + targetUserId).print();
                    break;
                }
                
                PrintBuilder.message("Enter Expense ID to delete: ").asPrompt().print();
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                
                if (dao.deleteById(deleteId)) {
                    PrintBuilder.message("Expense deleted successfully!").asSuccess().print();
                } else {
                    PrintBuilder.message("Expense not found.").asError().print();
                }
                break;
            } catch (Exception e) {
                PrintBuilder.message(e.getMessage() + "\nPlease try again.").asError().print();
                scanner.nextLine();
            }
        }
    }

    /**
     * Deletes all expense records (admin only)
     */
    public void deleteAllExpenses() throws SQLException {
        if (!isAdmin) {
            PrintBuilder.message("Admin access required").asError().print();
            return;
        }
        List<Expense> expenses = dao.getAllExpenses();
        for (Expense expense : expenses) {
            dao.deleteById(expense.getId());
        }
    }

    /**
     * Displays a list of expenses
     */
    private void displayExpenses(List<Expense> expenses, int userId) {
        if (expenses.isEmpty()) {
            PrintBuilder.message("No expense records found" + 
                (userId != 0 ? " for User ID: " + userId : "")).print();
        } else {
            PrintBuilder.message("\nExpense Records" + 
                (userId != 0 ? " for User ID: " + userId : "")).print();
            for (Expense expense : expenses) {
                PrintBuilder.message(expense.toString()).print();
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
        // cleaned = "20240102"
        // after substring = "2024"
        // after substring = "01"
        // after substring = "02"
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
     * Gets total expenses for a user or all users if admin
     */
    public void getTotalExpenses() throws SQLException {
        if (isAdmin) {
            PrintBuilder.message("Enter User ID (or 0 for all users): ").asPrompt().print();
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            
            List<Expense> expenses = targetUserId == 0 ? 
                dao.getAllExpenses() : 
                dao.getByUserId(targetUserId);
            
            double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
            
            PrintBuilder.message(String.format(
                "Total Expenses%s: $%.2f", 
                targetUserId == 0 ? "" : " for User " + targetUserId,
                total
            )).print();
        } else {
            List<Expense> expenses = dao.getByUserId(currentUserId);
            double total = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
            PrintBuilder.message(String.format(
                "Your Total Expenses: $%.2f", 
                total
            )).print();
        }
    }
}
