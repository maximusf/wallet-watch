// Copyright 2024 maximusf

package models;

public class Income {
    private int id;
    private int userId;
    private double amount;
    private String source;
    private String date;

    // Default constructor
    public Income () {
        this.id = 0;
        this.userId = 0;
        this.amount = 0;
        this.source = "";
        this.date = "";
    }

    // Parameterized constructor
    public Income(int id, int userId, double amount, String source, String date) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.source = source;
        this.date = date;
    }

    // Mutators
    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setDate(String date) {
        this.date = date;
    }
    // Accessors
    public int getId() {
        return this.id;
    }
    public int getUserId() {
        return this.userId;
    }
    public double getAmount() {
        return this.amount;
    }
    public String getSource() {
        return this.source;
    }
    public String getDate() {
        return this.date;
    }
}