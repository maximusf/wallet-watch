// Income.java
// Copyright 2024 maximusf

package models;

/**
 * Represents an income record in the database
 * Stores amount, source, date, and user information
 */
public class Income {
    private int id;         // Database ID (auto-generated)
    private int userId;     // User this income belongs to
    private double amount;  // Income amount in dollars
    private String source;  // Where the income came from
    private String date;    // Date in YYYY-MM-DD format

    // Create new income record
    public Income(int id, int userId, double amount, String source, String date) {
        // Validate input data
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format");
        }

        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.source = source;
        this.date = date;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.amount = amount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        this.source = source;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format");
        }
        this.date = date;
    }
}
