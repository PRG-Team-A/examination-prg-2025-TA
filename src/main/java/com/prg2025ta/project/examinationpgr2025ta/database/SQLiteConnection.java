package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection {
    private static SQLiteConnection INSTANCE = null;
    private Connection connection = null;

    private SQLiteConnection() {}

    public Connection getConnection() { return this.connection; }

    public static Connection getInstance() throws SQLException {
        if (INSTANCE == null || INSTANCE.getConnection().isClosed()) {
            INSTANCE = new SQLiteConnection();
            INSTANCE.init();
        }
        return INSTANCE.getConnection();
    }

    private void init() throws SQLException {
        connect();
    }

    public void connect() throws SQLException {
        String url = "jdbc:sqlite:database.db";
        connection = DriverManager.getConnection(url);
    }

    static void main() throws SQLException {
        DatabaseSetup.setup();
    }
}
