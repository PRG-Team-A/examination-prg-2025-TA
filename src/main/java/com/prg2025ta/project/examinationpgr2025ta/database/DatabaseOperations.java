package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        dbConnection.setAutoCommit(false);
        try (PreparedStatement prepared = dbConnection.prepareStatement("INSERT INTO products (product_uuid, display_name, price) VALUES (?, ?, ?);")) {
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

    // TODO: Add tests
    public GroceryProduct getProductByUUID(UUID uuid) throws SQLException {
        PreparedStatement statement = dbConnection
                .prepareStatement("SELECT product_uuid, display_name, price FROM products WHERE product_uuid = ?");

        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();

        boolean resultSetIsEmpty = !resultSet.next();

        if (!resultSetIsEmpty) {
            return null;
        }

        String product_id = resultSet.getString(1);
        String display_name = resultSet.getString(2);
        double price = resultSet.getDouble(3);

        if (product_id == null) return null;

        return new GroceryProduct(
                display_name,
                price,
                GroceryProduct.defaultDateOfExpiry,
                GroceryProduct.defaultNeedsCooling,
                UUID.fromString(product_id)
        );
    }

    public List<Product> getAllProducts() throws SQLException {
        Statement statement = dbConnection.createStatement();
        statement.execute("SELECT product_uuid, display_name, price FROM main.products;");

        ResultSet resultSet = statement.getResultSet();

        if (!resultSet.next()) {
            return null;
        }

        List<Product> products = new ArrayList<>();

        do {
            String uuid = resultSet.getString(1);
            String display_name = resultSet.getString(2);
            double price = resultSet.getDouble(3);

            products.add(new GroceryProduct(
                    uuid,
                    price,
                    GroceryProduct.defaultDateOfExpiry,
                    GroceryProduct.defaultNeedsCooling,
                    UUID.fromString(uuid)
            ));

        } while (resultSet.next());

        return products;
    }

    public void deleteProducts(List<UUID> uuids) throws SQLException {
        PreparedStatement statement = dbConnection
                .prepareStatement("DELETE FROM products WHERE product_uuid = ?");

        dbConnection.setAutoCommit(false);

        for (UUID uuid : uuids) {
            statement.setString(1, uuid.toString());
            statement.addBatch();
        }

        int[] result = statement.executeBatch();
        dbConnection.commit();

        System.out.println("Deleted " + result.length + " products.");
    }
}
