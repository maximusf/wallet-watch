-- Reset database to initial state
USE budget_tracker;

-- Temporarily disable safe update mode
SET SQL_SAFE_UPDATES = 0;

-- Clear existing data safely
DELETE FROM income WHERE id > 0;
DELETE FROM users WHERE id != 1;

-- Reset auto-increment sequences
ALTER TABLE income AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 2;

-- Ensure test user exists with consistent data
INSERT INTO users (id, name, email, password)
SELECT 1, 'Test User', 'test@example.com', 'password123'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE id = 1)
ON DUPLICATE KEY UPDATE
    name = 'Test User',
    email = 'test@example.com',
    password = 'password123';

-- Add some sample test data
INSERT INTO income (user_id, amount, source, date)
VALUES 
    (1, 2000.00, 'Salary', '2024-03-01'),
    (1, 500.00, 'Freelance', '2024-03-15');

-- Re-enable safe update mode
SET SQL_SAFE_UPDATES = 1;

-- Verify the reset
SELECT 'Users after reset:' as '';
SELECT * FROM users;
SELECT 'Income after reset:' as '';
SELECT * FROM income;
