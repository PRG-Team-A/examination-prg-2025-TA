CREATE TABLE IF NOT EXISTS "products"
(
    product_uuid  TEXT
        primary key,
    display_name  TEXT not null,
    price         NUMBER,
    needs_cooling INTEGER,
    expiry_date   INTEGER
);

CREATE TABLE IF NOT EXISTS warehouse
(
    warehouse_id integer not null
        constraint warehouse_pk
            primary key autoincrement,
    name         TEXT
);

CREATE TABLE IF NOT EXISTS sales
(
    sale_id integer primary key,
    paymentMethod TEXT,
    total REAL
);

CREATE TABLE IF NOT EXISTS sales_products
(
    sale_id integer NOT NULL,
    product_id TEXT NOT NULL
);