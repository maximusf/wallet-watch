// Income.java
// Copyright 2024 maximusf

package models;

public class Income extends Transaction {
    private String source;  // Where the income came from

    public Income(int id, int userId, double amount, String source, String date) {
        super(id, userId, amount, date);  // Call parent constructor
        
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        this.source = source;
    }

    public String getTransactionType() { return "Income"; }

    public String getSource() { return source; }

    public void setSource(String source) {
        if (source == null || source.trim().isEmpty()) {
            throw new IllegalArgumentException("Source cannot be empty");
        }
        this.source = source;
    }

    @Override
    public String toString() {
        return String.format("Income{id=%d, userId=%d, amount=%.2f, source='%s', date='%s'}",
            id, userId, amount, source, date);
    }
}