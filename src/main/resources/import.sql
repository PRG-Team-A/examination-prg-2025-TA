-- =============================================================================
-- Seed data for Supermarket Application (PostgreSQL + Spring Boot JPA)
-- Runs on startup when spring.sql.init.mode=always
-- Tables: employees, products (SINGLE_TABLE), sales, payments
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. EMPLOYEE (id: 1)
-- -----------------------------------------------------------------------------
INSERT INTO employees (id, username, password, role)
SELECT 1, 'Manager', 'manager123', 'MANAGER'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 2. PRODUCTS (5 varied: base Product + GroceryProduct, product_type)
--    GroceryProduct columns: date_of_expiry, needs_cooling
-- -----------------------------------------------------------------------------
INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 1, 'Milk', 3.50, 50, 'GROCERY', '2025-06-15', true
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 1);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 2, 'Bread', 2.00, 30, 'GROCERY', '2025-01-28', false
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 2);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 3, 'Yogurt', 1.80, 40, 'GROCERY', '2025-02-10', true
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 3);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 4, 'Detergent', 5.99, 20, 'Product', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 4);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 5, 'Rice 1kg', 4.75, 25, 'Product', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 5);

-- -----------------------------------------------------------------------------
-- 3. PAYMENT (id: 1) – required when using sale seeds
-- -----------------------------------------------------------------------------
INSERT INTO payments (id, amount, payment_method, timestamp)
SELECT 1, 19.04, 'CARD', '2025-01-25 10:30:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 4. SALE (id: 1) – employee 1, payment 1
-- -----------------------------------------------------------------------------
INSERT INTO sales (id, date, total, employee_id, payment_id)
SELECT 1, '2025-01-25', 19.04, 1, 1
WHERE NOT EXISTS (SELECT 1 FROM sales WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 5. SEQUENCES – avoid duplicate key on next insert
-- -----------------------------------------------------------------------------
SELECT setval('employees_id_seq', (SELECT COALESCE(MAX(id), 1) FROM employees));
SELECT setval('products_id_seq', (SELECT COALESCE(MAX(id), 1) FROM products));
SELECT setval('payments_id_seq', (SELECT COALESCE(MAX(id), 1) FROM payments));
SELECT setval('sales_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sales));
