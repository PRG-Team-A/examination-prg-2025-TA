package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    private static final String[] sql_to_execute = new String[] {
            "CREATE TABLE IF NOT EXISTS products (product_uuid TEXT PRIMARY KEY, display_name TEXT NOT NULL, price NUMBER NOT NULL);",
    };

    public static void setup() throws SQLException {
        setup(SQLiteConnection.getInstance());
    }

    public static void setup(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();

        for (String sql : sql_to_execute) {
            statement.execute(sql);
        }

    }
}
