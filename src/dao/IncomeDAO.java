// Copyright 2024 maximusf
// Income Data Access Object (DAO)
// Handles database operations for Income records
package dao; // package declaration

import java.sql.*;
import models.Income;
import java.util.List;
import java.util.ArrayList;

public class IncomeDAO {
    private final Connection connection;

    // Initializes DAO with database connection
    public IncomeDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to add a new income record to the database
    public void addIncome(Income income) throws SQLException {
        String query = "INSERT INTO income (user_id, amount, source, date) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, income.getUserId());
            stmt.setDouble(2, income.getAmount());
            stmt.setString(3, income.getSource());
            stmt.setString(4, income.getDate());
            stmt.executeUpdate();
        }
    }

    // Retrieves all income records for specified user
    public List<Income> getIncomeByUserId(int userId) throws SQLException {
        // SQL query to retrieve income records for a specific user
        String query = "SELECT * FROM income WHERE user_id = ?";
        List<Income> incomeList = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    incomeList.add(extractIncomeFromResultSet(rs));
                }
            }
        }
        return incomeList;
    }

    // Maps ResultSet row to Income object
    private Income extractIncomeFromResultSet(ResultSet rs) throws SQLException {
        return new Income(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getDouble("amount"),
            rs.getString("source"),
            rs.getString("date")
        );
    }

    // Updates an existing income record in the database
    public void updateIncome(Income income) throws SQLException {
        String query = "UPDATE income SET amount = ?, source = ?, date = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, income.getAmount());
            stmt.setString(2, income.getSource());
            stmt.setString(3, income.getDate());
            stmt.setInt(4, income.getId());
            stmt.executeUpdate();
        }
    }

    // Batch processes multiple income records for efficiency
    public void addIncomes(List<Income> incomes) throws SQLException {
        String query = "INSERT INTO income (user_id, amount, source, date) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (Income income : incomes) {
                stmt.setInt(1, income.getUserId());
                stmt.setDouble(2, income.getAmount());
                stmt.setString(3, income.getSource());
                stmt.setString(4, income.getDate());
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    
    // Deletes an income record from the database
    public void deleteIncome(int incomeId) throws SQLException {
        String query = "DELETE FROM income WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, incomeId);
            stmt.executeUpdate();
        }
    }
}
