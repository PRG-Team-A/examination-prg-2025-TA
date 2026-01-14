package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {
    private Connection dbConnection;

    private static DatabaseOperations INSTANCE;

    private DatabaseOperations(Connection connection) {
        this.dbConnection = connection;
    }

    public static DatabaseOperations getInstance() throws SQLException {
        if (INSTANCE == null || INSTANCE.dbConnection.isClosed()) {
            INSTANCE = new DatabaseOperations(SQLiteConnection.getInstance());
        }
        return INSTANCE;
    }

    public void insertNewProduct(Product product) throws SQLException {
        List<Product> products = new ArrayList<>();
        products.add(product);
        insertMultipleProducts(products);
    }

    public void insertMultipleProducts(List<Product> products) throws SQLException {
        PreparedStatement prepared = dbConnection.prepareCall("INSERT INTO products (product_uuid, display_name, price) VALUES (?, ?, ?)");

        dbConnection.setAutoCommit(false);

        for (Product product : products) {
            prepared.setString(1, product.getUuid().toString());
            prepared.setString(2, product.getDisplayName());
            prepared.setDouble(3, product.getPrice());
            prepared.addBatch();
        }

        int[] results = prepared.executeBatch();
        dbConnection.commit();

        System.out.println("Inserted " + results.length + " products.");
    }
}
