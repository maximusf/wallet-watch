-- Copyright 2024 maximusf
-- Sample queries for Wallet-Watch

-- User Queries
-- Find user by email
SELECT * FROM users WHERE email = 'test@example.com';

-- Income Queries
-- Get total income by user
SELECT user_id, SUM(amount) as total_income 
FROM income 
GROUP BY user_id;

-- Get income by date range
SELECT * FROM income 
WHERE user_id = 1 
AND date BETWEEN '2024-01-01' AND '2024-12-31';

-- Get income by source
SELECT source, SUM(amount) as total 
FROM income 
WHERE user_id = 1 
GROUP BY source;

-- Monthly income summary
SELECT 
    DATE_FORMAT(date, '%Y-%m') as month,
    SUM(amount) as monthly_total
FROM income 
WHERE user_id = 1
GROUP BY DATE_FORMAT(date, '%Y-%m')
ORDER BY month DESC;

-- Data Validation Queries
-- Find negative amounts
SELECT * FROM income WHERE amount < 0;

-- Find invalid dates
SELECT * FROM income WHERE date > CURRENT_DATE;

-- Find orphaned records
SELECT i.* 
FROM income i 
LEFT JOIN users u ON i.user_id = u.id 
WHERE u.id IS NULL;

-- Debugging Queries
-- Get latest income entries
SELECT * FROM income 
ORDER BY id DESC LIMIT 5;

-- Count records by user
SELECT user_id, COUNT(*) as record_count 
FROM income 
GROUP BY user_id;
