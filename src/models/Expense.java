// Expense.java
// Copyright 2024 maximusf

// Expense class extends Transaction class

package models;

public class Expense extends Transaction {
    private String category;  // Type of expense

    public Expense(int id, int userId, double amount, String category, String date) {
        super(id, userId, amount, date);  // Call parent constructor
        
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("Category cannot be empty");
        }
        this.category = category;
    }

    @Override
    public String getTransactionType() { return "Expense"; }

    @Override
    public String toString() {
        return String.format("Expense{id=%d, userId=%d, amount=%.2f, category='%s', date='%s'}",
            id, userId, amount, category, date);
    }
}
