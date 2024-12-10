// Main.java
// Copyright 2024 maximusf

import dao.IncomeDAO;
import models.Income;
import util.Environment;
import dao.ExpenseDAO;
import models.Expense;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int ADMIN_ID = 777;
    private static boolean isAdmin = false;
    private static int currentUserId = -1;
    private static Scanner scanner;

    // main
    public static void main(String[] args) {
        String url = Environment.get("DB_URL");
        String username = Environment.get("DB_USER");
        String password = Environment.get("DB_PASS");

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Scanner scannerInput = new Scanner(System.in)) {
            scanner = scannerInput;
            
            // User Authentication
            while (currentUserId == -1) {
                try {
                    System.out.print("Enter User ID: ");
                    int inputUserId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    
                    if (inputUserId == ADMIN_ID) {
                        isAdmin = true;
                        currentUserId = ADMIN_ID;
                        System.out.println("Administrator access granted.");
                    } else {
                        // Here you would validate if the user exists
                        currentUserId = inputUserId;
                        System.out.println("User access granted.");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter a valid User ID.");
                    scanner.nextLine(); // Clear invalid input
                }
            }

            IncomeDAO incomeDAO = new IncomeDAO(connection);
            ExpenseDAO expenseDAO = new ExpenseDAO(connection);

            while (true) {
                try {
                    displayMenu(isAdmin);
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (choice == 5) { // Exit
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }

                    switch (choice) {
                        case 1: // Add Income
                            addIncome(incomeDAO);
                            break;
                        case 2: // View Income
                            viewIncome(incomeDAO);
                            break;
                        case 3: // Add Expense
                            addExpense(expenseDAO);
                            break;
                        case 4: // View Expenses
                            viewExpenses(expenseDAO);
                            break;
                        case 6: // Update Income (Admin only)
                            updateIncome(incomeDAO);
                            break;
                        case 7: // Delete Income (Admin only)
                            deleteIncome(incomeDAO);
                            break;
                        case 8: // Update Expense (Admin only)
                            updateExpense(expenseDAO);
                            break;
                        case 9: // Delete Expense (Admin only)
                            deleteExpense(expenseDAO);
                            break;
                        case 10: // View All Users (Admin only)
                            if (!isAdmin) {
                                System.out.println("Admin access required");
                                break;
                            }
                            System.out.println("View all users functionality coming soon!");
                            break;
                        default:
                            System.out.println("Invalid option. Please try again.");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                    scanner.nextLine(); // Clear any bad input
                }
            }
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }

    // utility functions
    private static void displayMenu(boolean isAdmin) {
        System.out.println("\n=== Wallet-Watch Menu ===");
        System.out.println("1. Add Income");
        System.out.println("2. View Income");
        System.out.println("3. Add Expense");
        System.out.println("4. View Expenses");
        
        if (isAdmin) {
            System.out.println("6. Update Income");
            System.out.println("7. Delete Income");
            System.out.println("8. Update Expense");
            System.out.println("9. Delete Expense");
            System.out.println("10. View All Users");
        }
        System.out.println("5. Exit");
    }

    private static String formatDate(String input) {
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

        // Additional validation for days in month
        if (month == 2) {
            boolean isLeapYear = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
            if (day > (isLeapYear ? 29 : 28)) {
                throw new IllegalArgumentException("Invalid day for February");
            }
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            throw new IllegalArgumentException("Invalid day for month");
        }

        return String.format("%d-%02d-%02d", year, month, day);
    }   

    // income functions
    private static void addIncome(IncomeDAO dao) {
        while (true) {
            try {
                System.out.print("Enter Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter Source: ");
                String source = scanner.nextLine();
                
                String date;
                while (true) {
                    try {
                        System.out.print("Enter Date (YYYYMMDD): ");
                        date = formatDate(scanner.nextLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                Income income = new Income(0, currentUserId, amount, source, date);
                dao.add(income);
                System.out.println("Income added successfully!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    private static void viewIncome(IncomeDAO dao) throws SQLException {
        if (isAdmin) {
            System.out.print("Enter User ID to view (or 0 for all users): ");
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            List<Income> incomes = targetUserId == 0 ? dao.getAllIncomes() : dao.getByUserId(targetUserId);
            displayIncomes(incomes, targetUserId);
        } else {
            List<Income> incomes = dao.getByUserId(currentUserId);
            displayIncomes(incomes, currentUserId);
        }
    }

    private static void updateIncome(IncomeDAO dao) {
        if (!isAdmin) {
            System.out.println("Admin access required");
            return;
        }
        
        while (true) {
            try {
                System.out.print("Enter User ID: ");
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Income ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new Amount: ");
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter new Source: ");
                String newSource = scanner.nextLine();
                
                String newDate;
                while (true) {
                    try {
                        System.out.print("Enter new Date (YYYYMMDD): ");
                        newDate = formatDate(scanner.nextLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                Income updatedIncome = new Income(updateId, targetUserId, newAmount, newSource, newDate);
                dao.update(updatedIncome);
                System.out.println("Income updated successfully!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    private static void deleteIncome(IncomeDAO dao) {
        if (!isAdmin) {
            System.out.println("Admin access required");
            return;
        }

        while (true) {
            try {
                System.out.print("Enter User ID: ");
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                // First verify if user has any incomes
                List<Income> incomes = dao.getByUserId(targetUserId);
                if (incomes.isEmpty()) {
                    System.out.println("No income records found for User ID: " + targetUserId);
                    break;
                }
                
                System.out.print("Enter Income ID to delete: ");
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                
                if (dao.deleteById(deleteId)) {
                    System.out.println("Income deleted successfully!");
                } else {
                    System.out.println("Income not found.");
                }
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine();
            }
        }
    }

    private static void displayIncomes(List<Income> incomes, int userId) {
        if (incomes.isEmpty()) {
            System.out.println("No income records found" + (userId != 0 ? " for User ID: " + userId : ""));
        } else {
            System.out.println("\nIncome Records" + (userId != 0 ? " for User ID: " + userId : ""));
            for (Income income : incomes) {
                System.out.println(income.toString());
            }
        }
    }

    // expense functions
    private static void addExpense(ExpenseDAO dao) {
        while (true) {
            try {
                System.out.print("Enter Amount: ");
                double amount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter Category: ");
                String category = scanner.nextLine();
                
                String date;
                while (true) {
                    try {
                        System.out.print("Enter Date (YYYYMMDD): ");
                        date = formatDate(scanner.nextLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                Expense expense = new Expense(0, currentUserId, amount, category, date);
                dao.add(expense);
                System.out.println("Expense added successfully!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    private static void viewExpenses(ExpenseDAO dao) throws SQLException {
        if (isAdmin) {
            System.out.print("Enter User ID to view (or 0 for all users): ");
            int targetUserId = scanner.nextInt();
            scanner.nextLine();
            List<Expense> expenses = targetUserId == 0 ? dao.getAllExpenses() : dao.getByUserId(targetUserId);
            displayExpenses(expenses, targetUserId);
        } else {
            List<Expense> expenses = dao.getByUserId(currentUserId);
            displayExpenses(expenses, currentUserId);
        }
    }

    private static void updateExpense(ExpenseDAO dao) {
        if (!isAdmin) {
            System.out.println("Admin access required");
            return;
        }
        
        while (true) {
            try {
                System.out.print("Enter User ID: ");
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter Expense ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new Amount: ");
                double newAmount = scanner.nextDouble();
                scanner.nextLine();
                System.out.print("Enter new Category: ");
                String newCategory = scanner.nextLine();
                
                String newDate;
                while (true) {
                    try {
                        System.out.print("Enter new Date (YYYYMMDD): ");
                        newDate = formatDate(scanner.nextLine());
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                Expense updatedExpense = new Expense(updateId, targetUserId, newAmount, newCategory, newDate);
                dao.update(updatedExpense);
                System.out.println("Expense updated successfully!");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    private static void deleteExpense(ExpenseDAO dao) {
        if (!isAdmin) {
            System.out.println("Admin access required");
            return;
        }

        while (true) {
            try {
                System.out.print("Enter User ID: ");
                int targetUserId = scanner.nextInt();
                scanner.nextLine();
                
                // First verify if user has any expenses
                List<Expense> expenses = dao.getByUserId(targetUserId);
                if (expenses.isEmpty()) {
                    System.out.println("No expense records found for User ID: " + targetUserId);
                    break;
                }
                
                System.out.print("Enter Expense ID to delete: ");
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                
                if (dao.deleteById(deleteId)) {
                    System.out.println("Expense deleted successfully!");
                } else {
                    System.out.println("Expense not found.");
                }
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage() + "\nPlease try again.");
                scanner.nextLine();
            }
        }
    }

    private static void displayExpenses(List<Expense> expenses, int userId) {
        if (expenses.isEmpty()) {
            System.out.println("No expense records found" + (userId != 0 ? " for User ID: " + userId : ""));
        } else {
            System.out.println("\nExpense Records" + (userId != 0 ? " for User ID: " + userId : ""));
            for (Expense expense : expenses) {
                System.out.println(expense.toString());
            }
        }
    }
}
