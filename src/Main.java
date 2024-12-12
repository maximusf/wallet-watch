// Main.java
// Copyright 2024 maximusf

// Project-specific imports
import dao.*;          // Database Access Objects (IncomeDAO, ExpenseDAO)
import services.*;     // Service layer (IncomeService, ExpenseService)
import util.*;        // Utility classes (PrintBuilder, Environment)

// Java standard imports
import java.sql.Connection; // for database connection
import java.sql.DriverManager; // for database connection
import java.sql.SQLException; // for database errors
import java.util.Scanner; // for user input

/**
 * Main application class for Wallet-Watch
 * Handles user authentication, menu display, and command routing
 */
public class Main {
    private static final int ADMIN_ID = 777;
    private static boolean isAdmin = false;
    private static int currentUserId = -1;
    private static Scanner scanner;
    private static IncomeService incomeService;
    private static ExpenseService expenseService;

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                Environment.get("DB_URL"),
                Environment.get("DB_USER"),
                Environment.get("DB_PASS"));
             Scanner scannerInput = new Scanner(System.in)) {
            
            scanner = scannerInput;
            authenticateUser();
            setupServices(connection);
            runMainLoop();
            
        } catch (SQLException e) {
            PrintBuilder.message("Database error: " + e.getMessage()).asError().print();
        }
    }

    // Setup services - creates the services and passes the connection, scanner, currentUserId, and isAdmin
    private static void setupServices(Connection connection) {
        incomeService = new IncomeService(new IncomeDAO(connection), scanner, currentUserId, isAdmin);
        expenseService = new ExpenseService(new ExpenseDAO(connection), scanner, currentUserId, isAdmin);
    }

    // Main program loop - displays the menu and handles user input
    private static void runMainLoop() {
        while (true) {
            try {
                displayMenu(isAdmin);
                PrintBuilder.message("Enter your choice: ").asPrompt().print();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                if (choice == 5) {
                    PrintBuilder.message("Exiting... Goodbye!").asSuccess().print();
                    return;
                }

                handleMenuChoice(choice);
            } catch (SQLException e) {
                PrintBuilder.message("Database error: " + e.getMessage()).asError().print();
            } catch (NumberFormatException e) {
                PrintBuilder.message("Invalid input: Please enter a number").asError().print();
                scanner.nextLine(); // Clear bad input
            } catch (Exception e) {
                PrintBuilder.message("Error: " + e.getMessage()).asError().print();
                scanner.nextLine(); // Clear any bad input
            }
        }
    }

    // Authenticates the user - gets the user id and checks if the user is an admin
    private static void authenticateUser() {
        // TODO: Add a login system
        // currentUserId is the user id of the current user
        // isAdmin is a boolean that is true if the user is an admin
        while (currentUserId == -1) {
            try {
                PrintBuilder.message("Enter User ID: ").asPrompt().print();
                int inputUserId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                
                if (inputUserId == ADMIN_ID) {
                    isAdmin = true;
                    currentUserId = ADMIN_ID;
                    PrintBuilder.message("Administrator access granted.").asSuccess().print();
                } else if (inputUserId > 0) {
                    currentUserId = inputUserId;
                    PrintBuilder.message("User access granted.").asSuccess().print();
                } else {
                    PrintBuilder.message("Invalid User ID. Please enter a positive number.").asError().print();
                }
            } catch (Exception e) {
                PrintBuilder.message("Invalid input. Please enter a valid User ID.").asError().print();
                scanner.nextLine(); // Clear invalid input
            }
        }
    }

    // Handles the menu choice - switches the choice to the correct function
    private static void handleMenuChoice(int choice) throws SQLException {
        switch (choice) {
            case 1: incomeService.addIncome(); break;
            case 2: incomeService.viewIncome(); break;
            case 3: expenseService.addExpense(); break;
            case 4: expenseService.viewExpenses(); break;
            case 6: incomeService.updateIncome(); break;
            case 7: incomeService.deleteIncome(); break;
            case 8: expenseService.updateExpense(); break;
            case 9: expenseService.deleteExpense(); break;
            case 10: showBalance(); break;
            case 11: // View All Users
                if (!isAdmin) {
                    PrintBuilder.message("Admin access required").asError().print();
                    break;
                }
                PrintBuilder.message("View all users functionality coming soon!").print();
                break;
            case 12: // Reset All Tables
                if (!isAdmin) {
                    PrintBuilder.message("Admin access required").asError().print();
                    break;
                }
                resetAllTables();
                break;
            default:
                PrintBuilder.message("Invalid option. Please try again.").asError().print();
                break;
        }
    }

    // Displays the menu - displays the menu based on if the user is an admin or not
    private static void displayMenu(boolean isAdmin) {
        String[] baseOptions = {
            "Add Income",
            "View Income",
            "Add Expense",
            "View Expenses",
            "Exit",
            "Show Balance"
        };
        
        if (isAdmin) {
            String[] adminOptions = {
                "Add Income",
                "View Income",
                "Add Expense",
                "View Expenses",
                "Exit",
                "Update Income",
                "Delete Income",
                "Update Expense",
                "Delete Expense",
                "Show Balance",
                "View All Users",
                "Reset All Tables"
            };
            PrintBuilder.menu(adminOptions);
        } else {
            PrintBuilder.menu(baseOptions);
        }
    }

    // Shows the balance - shows the balance of the user
    private static void showBalance() throws SQLException {
        incomeService.getTotalIncome();
        expenseService.getTotalExpenses();
    }

    // Resets all tables - resets all income/expense tables
    private static void resetAllTables() {
        try {
            PrintBuilder.message("Are you sure you want to delete all records? (y/n): ").asPrompt().print();
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if (confirm.equals("y")) {
                // Delete all expenses first (due to foreign key constraints)
                expenseService.deleteAllExpenses();
                // Then delete all incomes
                incomeService.deleteAllIncomes();
                PrintBuilder.message("All records deleted successfully!").asSuccess().print();
            } else {
                PrintBuilder.message("Operation cancelled.").print();
            }
        } catch (SQLException e) {
            PrintBuilder.message("Error resetting tables: " + e.getMessage()).asError().print();
        }
    }
}
