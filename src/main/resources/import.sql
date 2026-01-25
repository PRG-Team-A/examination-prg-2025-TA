-- =============================================================================
-- Seed data para Supermarket Application (PostgreSQL + Spring Boot JPA)
-- Ejecutado al arranque si spring.sql.init.mode=always
-- Tablas: employees, products (SINGLE_TABLE), sales, payments
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 1. EMPLEADO (id: 1)
-- -----------------------------------------------------------------------------
INSERT INTO employees (id, username, password, role)
SELECT 1, 'Admin', 'admin123', 'ADMIN'
WHERE NOT EXISTS (SELECT 1 FROM employees WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 2. PRODUCTOS (5 variados: Product base + GroceryProduct, product_type)
--    Columnas GroceryProduct: date_of_expiry, needs_cooling
-- -----------------------------------------------------------------------------
INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 1, 'Leche entera', 3.50, 50, 'GROCERY', '2025-06-15', true
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 1);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 2, 'Pan integral', 2.00, 30, 'GROCERY', '2025-01-28', false
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 2);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 3, 'Yogur natural', 1.80, 40, 'GROCERY', '2025-02-10', true
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 3);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 4, 'Detergente', 5.99, 20, 'Product', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 4);

INSERT INTO products (id, name, price, stock, product_type, date_of_expiry, needs_cooling)
SELECT 5, 'Arroz 1kg', 4.75, 25, 'Product', NULL, NULL
WHERE NOT EXISTS (SELECT 1 FROM products WHERE id = 5);

-- -----------------------------------------------------------------------------
-- 3. PAGO (id: 1) – necesario si usas seeds de ventas
-- -----------------------------------------------------------------------------
INSERT INTO payments (id, amount, payment_method, timestamp)
SELECT 1, 19.04, 'CARD', '2025-01-25 10:30:00'
WHERE NOT EXISTS (SELECT 1 FROM payments WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 4. VENTA (id: 1) – empleado 1, pago 1
-- -----------------------------------------------------------------------------
INSERT INTO sales (id, date, total, employee_id, payment_id)
SELECT 1, '2025-01-25', 19.04, 1, 1
WHERE NOT EXISTS (SELECT 1 FROM sales WHERE id = 1);

-- -----------------------------------------------------------------------------
-- 5. SECUENCIAS – evitar “llave primaria duplicada” en próximos inserts
-- -----------------------------------------------------------------------------
SELECT setval('employees_id_seq', (SELECT COALESCE(MAX(id), 1) FROM employees));
SELECT setval('products_id_seq', (SELECT COALESCE(MAX(id), 1) FROM products));
SELECT setval('payments_id_seq', (SELECT COALESCE(MAX(id), 1) FROM payments));
SELECT setval('sales_id_seq', (SELECT COALESCE(MAX(id), 1) FROM sales));
