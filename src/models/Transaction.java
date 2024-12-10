// Transaction.java
// Copyright 2024 maximusf

package models;

/**
 * Base class for financial transactions
 * Contains common properties shared by Income and Expense
 */
public abstract class Transaction {
    protected int id;         // Database ID (auto-generated)
    protected int userId;     // User this transaction belongs to
    protected double amount;  // Transaction amount in dollars
    protected String date;    // Date in YYYY-MM-DD format

    // Constructor with common validation
    public Transaction(int id, int userId, double amount, String date) {
        // Validate input data
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format");
        }

        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.date = date;
    }

    // Common getters and setters
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        if (!date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in YYYY-MM-DD format");
        }
        this.date = date;
    }

    // Abstract method that child classes must implement
    public abstract String getTransactionType();
}
