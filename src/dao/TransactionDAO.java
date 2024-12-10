// TransactionDAO.java
// Copyright 2024 maximusf

// TransactionDAO class is a base class for database operations on transactions

package dao;

import java.sql.*;
import java.util.List;
import util.Environment;

/**
 * Base class for database operations on transactions
 * @param <T> The type of transaction (Income or Expense)
 */
public abstract class TransactionDAO<T> {
    // Database connection settings from .env file
    protected static final String URL = Environment.get("DB_URL");
    protected static final String USER = Environment.get("DB_USER");
    protected static final String PASS = Environment.get("DB_PASS");

    protected Connection conn;
    protected final String tableName;

    public TransactionDAO(Connection conn, String tableName) {
        this.conn = conn;
        this.tableName = tableName;
    }

    // Connect to database if needed
    public void connect() throws SQLException {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(URL, USER, PASS);
        }
    }

    // Close database connection
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // Delete record by ID
    public boolean deleteById(int id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE id = ?", tableName);
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    // Get records by user ID
    public abstract List<T> getByUserId(int userId) throws SQLException;

    // Add new record
    public abstract T add(T transaction) throws SQLException;

    // Update existing record
    public abstract boolean update(T transaction) throws SQLException;
}
