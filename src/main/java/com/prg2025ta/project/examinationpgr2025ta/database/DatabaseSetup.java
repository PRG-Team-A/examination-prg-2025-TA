package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String[] sql_to_execute = new String[] {
            """
CREATE TABLE IF NOT EXISTS "products"
(
    product_uuid  TEXT
        primary key,
    display_name  TEXT not null,
    price         NUMBER,
    needs_cooling INTEGER,
    expiry_date   INTEGER
);""",
            """
CREATE TABLE IF NOT EXISTS warehouse
(
    warehouse_id integer not null
        constraint warehouse_pk
            primary key autoincrement,
    name         TEXT
);
""",
            """
CREATE TABLE IF NOT EXISTS sales
(
    sale_id integer primary key,
    customerId integer,
    paymentMethod TEXT,
    total REAL
);
""",
            """
CREATE TABLE IF NOT EXISTS sales_products
(
    sale_id integer NOT NULL,
    product_id TEXT NOT NULL
);
"""
    };

    public static void setup() throws SQLException {
        setup(SQLiteConnection.getInstance());
    }

    public static void setup(Connection connection) throws SQLException {
        PreparedStatement preparedStatement;

        for (String sql : sql_to_execute) {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        }

    }
}
