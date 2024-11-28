// Copyright 2024 maximusf
// Income Data Access Object

package dao;

import java.sql.*;
import models.Income;

public class IncomeDAO {
    private Connection connection;

    public IncomeDAO(Connection connection) {
        this.connection = connection;
    }

    // Add an income to the database
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
}
