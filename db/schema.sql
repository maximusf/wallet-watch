-- Copyright 2024 maximusf
-- Database schema for Wallet-Watch application

-- Create database if it doesn't exist
CREATE DATABASE IF NOT EXISTS budget_tracker;
USE budget_tracker;

-- Users Table - Core user information
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,  -- Increased length for diverse names
    email VARCHAR(320) UNIQUE NOT NULL,  -- Increased length for email standards
    password VARCHAR(255) NOT NULL  -- Appropriate for storing hashed passwords
);

-- Income Table - Income transaction records
CREATE TABLE IF NOT EXISTS income (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,          -- Links to user account
    amount DECIMAL(10,2) NOT NULL, -- Money amount (max 99,999,999.99)
    source VARCHAR(255) NOT NULL,  -- Where the money came from
    date DATE NOT NULL            -- When the income was received
);

-- Expenses Table - Expense transaction records
CREATE TABLE IF NOT EXISTS expenses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL, -- Amount of expense, can be negative for income
    category VARCHAR(50), -- Category of expense, e.g., 'Food', 'Rent', 'Transportation'
    date DATE NOT NULL, -- Date of the expense transaction
    FOREIGN KEY (user_id) REFERENCES users(id) 
);

-- Savings Goals Table - User savings targets
CREATE TABLE IF NOT EXISTS savings_goals (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL, 
    goal_amount DECIMAL(10, 2) NOT NULL, -- Target amount for the savings goal
    current_amount DECIMAL(10, 2) DEFAULT 0, -- Current amount saved towards the goal
    start_date DATE NOT NULL, -- Start date of the savings goal
    end_date DATE NOT NULL, -- End date of the savings goal
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Populating database with sample data
INSERT INTO users (name, email, password) 
VALUES ('John Doe', 'john@example.com', 'password123');

INSERT INTO income (user_id, amount, source, date)
VALUES (1, 2000.00, 'Salary', '2024-11-01');

INSERT INTO expenses (user_id, amount, category, date)
VALUES (1, 500.00, 'Rent', '2024-11-02');

INSERT INTO savings_goals (user_id, goal_amount, current_amount, start_date, end_date)
VALUES (1, 5000.00, 1000.00, '2024-11-01', '2025-01-01');

-- Querying the data
SELECT * FROM users;
SELECT * FROM income;
SELECT * FROM expenses;
SELECT * FROM savings_goals;


-- NOTE: Use CTRL + E twice to run the selected query code