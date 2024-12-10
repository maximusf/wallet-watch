// Copyright 2024 maximusf
// Income Data Access Object (DAO)

// Handles database operations for Income records
package dao; // package declaration

import java.sql.*;
import models.Income;
import java.util.List;
import java.util.ArrayList;
import util.Environment;

/**
 * Handles all database operations for Income records
 * This class connects to MySQL and manages income data
 */
public class IncomeDAO {
    // Database connection settings from .env file
    private static final String URL = Environment.get("DB_URL");
    private static final String USER = Environment.get("DB_USER");
    private static final String PASS = Environment.get("DB_PASS");

    private Connection conn;

    /**
     * Constructor that takes an existing connection
     * @param conn Database connection
     */
    public IncomeDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates a new database connection
     * @throws SQLException if connection fails
     */
    public void connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
    }

    /**
     * Closes the database connection
     * @throws SQLException if closing fails
     */
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Adds a new income record to the database
     * @param income Income object to add
     * @return The added Income object with generated ID
     * @throws SQLException if database operation fails
     */
    public Income addIncome(Income income) throws SQLException {
        String sql = "INSERT INTO income (user_id, amount, source, date) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, income.getUserId());
            stmt.setDouble(2, income.getAmount());
            stmt.setString(3, income.getSource());
            stmt.setString(4, income.getDate());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating income failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    income.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating income failed, no ID obtained.");
                }
            }
        }
        
        return income;
    }

    /**
     * Retrieves all income records for a specific user
     * @param userId The user's ID
     * @return List of Income objects
     * @throws SQLException if database operation fails
     */
    public List<Income> getIncomeByUserId(int userId) throws SQLException {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income WHERE user_id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Income income = new Income(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("source"),
                        rs.getString("date")
                    );
                    incomes.add(income);
                }
            }
        }
        
        return incomes;
    }

    /**
     * Deletes an income record by its ID
     * @param id The income record ID to delete
     * @return true if deletion was successful
     * @throws SQLException if database operation fails
     */
    public boolean deleteIncome(int id) throws SQLException {
        String sql = "DELETE FROM income WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Updates an existing income record
     * @param income Income object with updated values
     * @return true if update was successful
     * @throws SQLException if database operation fails
     */
    public boolean updateIncome(Income income) throws SQLException {
        String sql = "UPDATE income SET amount = ?, source = ?, date = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, income.getAmount());
            stmt.setString(2, income.getSource());
            stmt.setString(3, income.getDate());
            stmt.setInt(4, income.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }
}
