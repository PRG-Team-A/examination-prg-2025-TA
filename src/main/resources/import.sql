-- Seed data for Supermarket Application
-- This file is automatically executed by Spring Boot on application startup
-- when spring.sql.init.mode=always is set in application.properties
-- Table names follow Spring JPA conventions: employees, products

-- Insert test Employee (ID: 1, username: 'Admin')
-- Note: Employee entity uses 'username' field, not 'name'
INSERT INTO employees (id, username, password, role) 
SELECT 1, 'Admin', 'admin123', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE id = 1);

-- Reset sequence to ensure next ID is 2 (if needed)
SELECT setval('employees_id_seq', (SELECT MAX(id) FROM employees));

-- Insert 3 test Products with prices and initial stock levels
-- Note: product_type is NULL for base Product class
-- For GroceryProduct, it would be 'GROCERY'
INSERT INTO products (name, price, stock, product_type) 
SELECT 'Milk', 3.50, 50, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Milk');

INSERT INTO products (name, price, stock, product_type) 
SELECT 'Bread', 2.00, 30, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Bread');

INSERT INTO products (name, price, stock, product_type) 
SELECT 'Rice', 4.75, 25, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Rice');
