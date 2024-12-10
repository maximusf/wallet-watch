-- reset_db.sql
-- Copyright 2024 maximusf
-- Reset database for testing
-- WARNING: This will delete all existing data!

USE budget_tracker;

-- Clear existing data from all tables (in correct order due to foreign keys)
DELETE FROM savings_goals;
DELETE FROM expenses;
DELETE FROM income;
DELETE FROM users;

-- Reset auto-increment counters
ALTER TABLE savings_goals AUTO_INCREMENT = 1;
ALTER TABLE expenses AUTO_INCREMENT = 1;
ALTER TABLE income AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;

-- Add test user
INSERT INTO users (id, name, email, password) 
VALUES (1, 'Test User', 'test@example.com', 'test123');

-- Add test income records
INSERT INTO income (user_id, amount, source, date) VALUES
    (1, 1000.00, 'Salary', '2024-03-15'),
    (1, 500.00, 'Freelance', '2024-03-16'),
    (1, 250.00, 'Side Project', '2024-03-17');

-- Add test expense records
INSERT INTO expenses (user_id, amount, category, date) VALUES
    (1, 800.00, 'Rent', '2024-03-15'),
    (1, 100.00, 'Groceries', '2024-03-16'),
    (1, 50.00, 'Transportation', '2024-03-17');

-- Add test savings goal
INSERT INTO savings_goals (user_id, goal_amount, current_amount, start_date, end_date) VALUES
    (1, 5000.00, 1000.00, '2024-03-15', '2024-12-31');
