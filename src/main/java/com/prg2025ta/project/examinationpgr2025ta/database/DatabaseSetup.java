package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String[] sql_to_execute = new String[] {
            """
CREATE TABLE IF NOT EXISTS products (
    product_uuid TEXT PRIMARY KEY,
    display_name TEXT NOT NULL,
    price NUMBER NOT NULL,
    product_type TEXT DEFAULT 'grocery',
    needs_cooling INTEGER DEFAULT 0,
    expiry_date INTEGER DEFAULT 0,
    tax_category TEXT DEFAULT 'STANDARD',
    is_premium INTEGER DEFAULT 0
);""",
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