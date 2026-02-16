package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
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
    public Product getProductByUUID(UUID uuid) throws SQLException {
        PreparedStatement statement = dbConnection
                .prepareStatement("SELECT product_uuid, display_name, price, needs_cooling, expiry_date FROM products WHERE product_uuid = ?");

        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();

        boolean resultSetIsEmpty = !resultSet.next();

        if (!resultSetIsEmpty) {
            return null;
        }

        String product_id = resultSet.getString("product_uuid");
        String display_name = resultSet.getString("display_name");
        double price = resultSet.getDouble("price");
        int needsCooling = resultSet.getInt("needs_cooling");
        long expiry_date = resultSet.getInt("expiry_date");

        if (product_id == null) return null;

        if (display_name != null && price != 0 && needsCooling != 0 && expiry_date != 0) {
            LocalDate dateOfExpiry = LocalDate.ofInstant(Instant.ofEpochSecond(expiry_date), ZoneId.of("UTC"));
            boolean doesProductNeedCooling = needsCooling == 2;
            return new GroceryProduct(
                    display_name,
                    price,
                    dateOfExpiry,
                    doesProductNeedCooling,
                    UUID.fromString(product_id)
            );
        }

        return null;
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        Statement statement = dbConnection.createStatement();
        statement.execute("SELECT product_uuid, display_name, price FROM main.products;");

        ResultSet resultSet = statement.getResultSet();

        if (!resultSet.next()) {
            return products;
        }

        do {
            String uuid = resultSet.getString("product_uuid");
            String display_name = resultSet.getString("display_name");
            double price = resultSet.getDouble("price");

            products.add(new GroceryProduct(
                    display_name,
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

    /**
     * <strong>ATTENTION!!!</strong>
     * This is really dangerous and should be handled with care.
     * It will delete ALL products from the production database.
     */
    public void nukeAllProducts() throws SQLException {
        Statement nukeStatement = dbConnection.createStatement();
        nukeStatement.execute("DELETE FROM products;");
    }

    public void close() throws SQLException {
        dbConnection.close();
    }
}
