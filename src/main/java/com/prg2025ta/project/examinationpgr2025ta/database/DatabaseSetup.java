package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseSetup {

    private static final String[] sql_to_execute = new String[] {
            "CREATE TABLE IF NOT EXISTS \"products\"\n" +
                    "(\n" +
                    "    product_uuid    TEXT PRIMARY KEY,\n" +
                    "    product_type    TEXT DEFAULT 'grocery',\n" +
                    "    display_name    TEXT NOT NULL,\n" +
                    "    price           NUMBER,\n" +
                    "    price_per_kg    NUMBER,\n" +
                    "    needs_cooling   INTEGER DEFAULT 0,\n" +
                    "    expiry_date     INTEGER DEFAULT 0,\n" +
                    "    tax_category    TEXT DEFAULT 'STANDARD',\n" +
                    "    is_premium      INTEGER DEFAULT 0,\n" +
                    "    warranty_years  INTEGER DEFAULT 0\n" +
                    ");",
            "CREATE TABLE IF NOT EXISTS warehouse\n" +
                    "(\n" +
                    "    warehouse_id integer not null\n" +
                    "        constraint warehouse_pk\n" +
                    "            primary key autoincrement,\n" +
                    "    name         TEXT\n" +
                    ");",
            "CREATE TABLE IF NOT EXISTS sales\n" +
                    "(\n" +
                    "    sale_id integer primary key,\n" +
                    "    customerId integer,\n" +
                    "    paymentMethod TEXT,\n" +
                    "    total REAL\n" +
                    ");",
            "CREATE TABLE IF NOT EXISTS sales_products\n" +
                    "(\n" +
                    "    sale_id integer NOT NULL,\n" +
                    "    product_id TEXT NOT NULL\n" +
                    ");"
    };

    public static void setup() throws SQLException {
        setup(SQLiteConnection.getInstance());
    }

    public static void setup(Connection connection) throws SQLException {
        for (String sql : sql_to_execute) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        }
    }
}