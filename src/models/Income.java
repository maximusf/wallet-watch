// Income.java
// Copyright 2024 maximusf

package models;

public class Income {
    private int id;
    private int userId;
    private double amount;
    private String source;
    private String date;

    // Default constructor
    public Income() {
        this.id = 0;
        this.userId = 0;
        this.amount = 0;
        this.source = "";
        this.date = "";
    }

    // Parameterized constructor
    public Income(int id, int userId, double amount, String source, String date) {
        setId(id);
        setUserId(userId);
        setAmount(amount);
        setSource(source);
        setDate(date);
    }

    // Getters
    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public String getDate() {
        return date;
    }

    // Setters with validation
    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative.");
        }
        this.id = id;
    }

    public void setUserId(int userId) {
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than zero.");
        }
        this.userId = userId;
    }

    public void setAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        this.amount = amount;
    }

    public void setSource(String source) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be null or empty.");
        }
        this.source = source;
    }

    public void setDate(String date) {
        if (date == null || !date.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Date must be in the format YYYY-MM-DD.");
        }
        this.date = date;
    }

    // Override toString() for display
    @Override
    public String toString() {
        return "Income ID: " + id +
                ", User ID: " + userId +
                ", Amount: " + amount +
                ", Source: '" + source + '\'' +
                ", Date: '" + date + '\'';
    }
}
