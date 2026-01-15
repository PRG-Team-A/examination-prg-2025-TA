package com.prg2025ta.project.examinationpgr2025ta.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection implements AutoCloseable {
    public static String dbURL = "jdbc:sqlite:database.db";
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
        connection = DriverManager.getConnection(SQLiteConnection.dbURL);
    }

    static void main() throws SQLException {
        DatabaseSetup.setup();
    }

    @Override
    public void close() throws Exception {
        this.connection.close();
    }
}
