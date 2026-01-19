package com.prg2025ta.project.examinationpgr2025ta.database;

import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteConnection implements AutoCloseable {
    public static String dbURL = "jdbc:sqlite:database.db";
    private static volatile SQLiteConnection INSTANCE = null;
    private Connection connection = null;

    private SQLiteConnection() {}

    public Connection getConnection() { return this.connection; }

    public synchronized static Connection getInstance() throws SQLException {
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

    @Override
    public void close() throws Exception {
        this.connection.close();
        INSTANCE = null;
    }
}
