// IncomeDAO.java
// Copyright 2024 maximusf  

// Handles database operations for Income records
package dao; // package declaration

import java.sql.*;
import models.Income;
import java.util.List;
import java.util.ArrayList;

/**
 * Handles all database operations for Income records
 * This class connects to MySQL and manages income data
 */
public class IncomeDAO extends TransactionDAO<Income> {
    public IncomeDAO(Connection conn) {
        super(conn, "income");
    }

    @Override
    public List<Income> getByUserId(int userId) throws SQLException {
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

    @Override
    public Income add(Income income) throws SQLException {
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

    @Override
    public boolean update(Income income) throws SQLException {
        String sql = "UPDATE income SET amount = ?, source = ?, date = ? WHERE id = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, income.getAmount());
            stmt.setString(2, income.getSource());
            stmt.setString(3, income.getDate());
            stmt.setInt(4, income.getId());
            
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Income> getAllIncomes() throws SQLException {
        List<Income> incomes = new ArrayList<>();
        String sql = "SELECT * FROM income";
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    incomes.add(new Income(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("source"),
                        rs.getString("date")
                    ));
                }
            }
        }
        return incomes;
    }

}
