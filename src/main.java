// Copyright 2024 maximusf

import models.Income;

public class Main {
    public static void main(String[] args) {
        Income income = new Income(1, 1, 1000, "Salary", "2024-01-01");
        
        // print all fields
        System.out.println("ID: " + income.getId());
        System.out.println("User ID: " + income.getUserId());
        System.out.println("Amount: " + income.getAmount());
        System.out.println("Source: " + income.getSource());
        System.out.println("Date: " + income.getDate());
    }
}
