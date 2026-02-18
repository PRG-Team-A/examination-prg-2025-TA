package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {
    private static final String[] sql_to_execute = new String[] {
            "CREATE TABLE IF NOT EXISTS products (product_uuid TEXT PRIMARY KEY, product_type TEXT DEFAULT 'grocery', display_name TEXT NOT NULL, price NUMBER, price_per_kg NUMBER, needs_cooling INTEGER DEFAULT 0, expiry_date INTEGER DEFAULT 0, tax_category TEXT DEFAULT 'STANDARD', is_premium INTEGER DEFAULT 0, warranty_years INTEGER DEFAULT 0);",
            "CREATE TABLE IF NOT EXISTS warehouse (warehouse_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, name TEXT);",
            "CREATE TABLE IF NOT EXISTS sales (sale_id INTEGER PRIMARY KEY, customerId INTEGER, paymentMethod TEXT, total REAL);",
            "CREATE TABLE IF NOT EXISTS sales_products (sale_id INTEGER NOT NULL, product_id TEXT NOT NULL);"
    };

    public static void setup() throws SQLException {
        setup(SQLiteConnection.getInstance());
    }

    public static void setup(Connection connection) throws SQLException {
        for (String sql : sql_to_execute) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.execute();
                System.out.println("Executed: " + sql.substring(0, 50) + "...");
            } catch (SQLException e) {
                System.err.println("Failed to execute SQL: " + sql);
                System.err.println("Error: " + e.getMessage());
                throw e;
            }
        }
    }
}