// Copyright 2024 maximusf
// Income Data Access Object (DAO)
// Handles database operations for Income records
package dao;

import java.sql.*;
import models.Income;

public class IncomeDAO {
    // Stores database connection
    private Connection connection;

    // Constructor that receives a database connection
    public IncomeDAO(Connection connection) {
        this.connection = connection;
    }

    // Method to insert a new income record into the database
    public void addIncome(Income income) throws SQLException {
        // SQL query with placeholders (?) to prevent SQL injection
        String query = "INSERT INTO income (user_id, amount, source, date) VALUES (?, ?, ?, ?)";
        
        // PreparedStatement automatically closes due to try-with-resources
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set values for each placeholder in order
            stmt.setInt(1, income.getUserId());    // user_id column
            stmt.setDouble(2, income.getAmount());  // amount column
            stmt.setString(3, income.getSource());  // source column
            stmt.setString(4, income.getDate());    // date column
            
            // Execute the insert statement
            stmt.executeUpdate();
        }
    }
}
