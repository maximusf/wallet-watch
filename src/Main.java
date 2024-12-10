// Main.java
// Copyright 2024 maximusf

import dao.IncomeDAO;
import models.Income;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/budget_tracker";
        String username = "root";  // Replace with your MySQL username
        String password = "Maroonblue98*";  // Replace with your MySQL password

        try (Connection connection = DriverManager.getConnection(url, username, password);
             Scanner scanner = new Scanner(System.in)) {
            IncomeDAO incomeDAO = new IncomeDAO(connection);

            while (true) {
                // Display menu options
                System.out.println("\n=== Wallet-Watch Menu ===");
                System.out.println("1. Add Income");
                System.out.println("2. View Income");
                System.out.println("3. Exit");
                System.out.println("4. Update Income");
                System.out.println("5. Delete Income");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1: // Add Income
                        System.out.print("Enter User ID: ");
                        int userId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Amount: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter Source: ");
                        String source = scanner.nextLine();
                        System.out.print("Enter Date (YYYY-MM-DD): ");
                        String date = scanner.nextLine();

                        Income income = new Income(0, userId, amount, source, date);
                        incomeDAO.addIncome(income);
                        System.out.println("Income added successfully!");
                        break;

                    case 2: // View Income
                        System.out.print("Enter User ID to view income: ");
                        userId = scanner.nextInt();
                        List<Income> incomes = incomeDAO.getIncomeByUserId(userId);

                        if (incomes.isEmpty()) {
                            System.out.println("No income records found for User ID: " + userId);
                        } else {
                            System.out.println("\nIncome Records:");
                            for (Income inc : incomes) {
                                System.out.println(inc.toString());
                            }
                        }
                        break;

                    case 3: // Exit
                        System.out.println("Exiting... Goodbye!");
                        return;

                    case 4: // Update Income
                        System.out.print("Enter Income ID to update: ");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new Amount: ");
                        double newAmount = scanner.nextDouble();
                        scanner.nextLine();
                        System.out.print("Enter new Source: ");
                        String newSource = scanner.nextLine();
                        System.out.print("Enter new Date (YYYY-MM-DD): ");
                        String newDate = scanner.nextLine();

                        Income updatedIncome = new Income(updateId, 0, newAmount, newSource, newDate);
                        incomeDAO.updateIncome(updatedIncome);
                        System.out.println("Income updated successfully!");
                        break;

                    case 5: // Delete Income
                        System.out.print("Enter Income ID to delete: ");
                        int deleteId = scanner.nextInt();
                        incomeDAO.deleteIncome(deleteId);
                        System.out.println("Income deleted successfully!");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database.");
        }
    }
}
