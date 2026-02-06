package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DatabaseOperations {
    private static DatabaseOperations INSTANCE;
    private Connection dbConnection;

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
        List<Product> products = Collections.singletonList(product);
        insertMultipleProducts(products);
    }

    public void insertMultipleProducts(List<Product> products) throws SQLException {
        dbConnection.setAutoCommit(false);
        Savepoint beforeInsertion = dbConnection.setSavepoint();
        try (PreparedStatement prepared = dbConnection.prepareStatement("INSERT INTO products (product_uuid, display_name, price) VALUES (?, ?, ?);")) {
            for (Product product : products) {
                prepared.setString(1, product.getUuid().toString());
                prepared.setString(2, product.getDisplayName());
                prepared.setDouble(3, product.getPrice());
                prepared.addBatch();
            }

            int[] results = prepared.executeBatch();
            dbConnection.commit();
            dbConnection.setAutoCommit(true);

            System.out.println("Inserted " + results.length + " products.");
        } catch (SQLException sqlException) {
            dbConnection.rollback(beforeInsertion);
            throw sqlException;
        }
    }

    // TODO: Add tests
    public Product getProductByUUID(UUID uuid) throws SQLException {
        PreparedStatement statement = dbConnection
                .prepareStatement("SELECT product_uuid, display_name, price, needs_cooling, expiry_date FROM products WHERE product_uuid = ?");

        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();

        boolean resultSetIsEmpty = !resultSet.next();

        if (resultSetIsEmpty) {
            return null;
        }

        String product_id = resultSet.getString("product_uuid");
        String display_name = resultSet.getString("display_name");
        double price = resultSet.getDouble("price");
        int needsCooling = resultSet.getInt("needs_cooling");
        long expiry_date = resultSet.getInt("expiry_date");

        if (product_id == null) return null;
        if (display_name == null) return null;

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

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        Statement statement = dbConnection.createStatement();
        statement.execute("SELECT product_uuid, display_name, price FROM main.products;");

        ResultSet resultSet = statement.getResultSet();

        if (!resultSet.next()) {
            return products;
        }

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

        dbConnection.setAutoCommit(false);

        System.out.println("Deleted " + result.length + " products.");
    }

    public List<SalesClass> getAllSales() throws SQLException {
        List<SalesClass> sales = new ArrayList<>();

        PreparedStatement selectStatement = dbConnection.prepareStatement(
                "SELECT sale_id, customerId, paymentMethod, total FROM sales;"
        );

        PreparedStatement getProductsFromSale = dbConnection.prepareStatement(
                "SELECT product_id FROM sales_products WHERE sale_id = ?;"
        );

        ResultSet resultSet = selectStatement.executeQuery();

        if (!resultSet.next()) {
            return sales;
        }

        do {
            int saleId = resultSet.getInt("sale_id");
            int customerId = resultSet.getInt("customerId");
            String paymentMethod = resultSet.getString("paymentMethod");
            double total = resultSet.getDouble("total");

            getProductsFromSale.setInt(1, saleId);
            ResultSet productsResultSet = getProductsFromSale.executeQuery();

            List<String> productIds = new ArrayList<>();
            List<Product> products = new ArrayList<>();

            if (productsResultSet.next()) {
                do {
                    productIds.add(productsResultSet.getString(1));
                } while (productsResultSet.next());
            }

            for (String productId : productIds) {
                products.add(getProductByUUID(UUID.fromString(productId)));
            }

            SalesClass sale = new SalesClass(
                    customerId,
                    paymentMethod,
                    products,
                    total
            );
            sales.add(sale);

        } while (resultSet.next());

        return sales;
    }

    /**
     * @return True if the operation was successful, false if it was not.
     * This operation is transaction based. If there is an error, the database will not
     * be modified. (at least I hope so)
     */
    public boolean insertSale(SalesClass sale) throws SQLException {
        dbConnection.setAutoCommit(false);

        PreparedStatement salesInsertStatement = dbConnection
                .prepareStatement(
                        "INSERT INTO sales (customerId, paymentMethod, total) VALUES (?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );

        PreparedStatement getIdStatement = dbConnection.prepareStatement(
                "SELECT last_insert_rowid();"
        );


        salesInsertStatement.setInt(1, sale.getCustomerID());
        salesInsertStatement.setString(2, sale.getPaymentMethod());
        salesInsertStatement.setDouble(3, sale.getTotal());
        salesInsertStatement.executeUpdate();

        PreparedStatement getInsertedSale = dbConnection.prepareStatement(
                "SELECT sale_id FROM sales ORDER BY sale_id DESC LIMIT 1;"
        );

        int saleId = getInsertedSale.executeQuery().getInt(1);

        PreparedStatement associateProductWithSaleStatement = dbConnection.prepareStatement(
                "INSERT INTO sales_products (sale_id, product_id) VALUES (?, ?)"
        );
        associateProductWithSaleStatement.setInt(1, saleId);

        List<Product> productsBought = sale.getProductsBought();
        for (int i = 0; i < productsBought.size(); i++) {
            associateProductWithSaleStatement.setString(2, productsBought.get(i).getUuid().toString());
            associateProductWithSaleStatement.addBatch();
        }

        int[] updateCounts = associateProductWithSaleStatement.executeBatch();
        boolean everythingWasSuccessfull = true;

        for (int i = 0; i < updateCounts.length; i++) {
            if (updateCounts[i] == Statement.EXECUTE_FAILED) {
                everythingWasSuccessfull = false;
            }
        }

        if (!everythingWasSuccessfull) {
            dbConnection.rollback();
        }

        dbConnection.setAutoCommit(true);
        return everythingWasSuccessfull;
    }

    private int getLastInsertedId() throws SQLException {
        PreparedStatement getIdStatement = dbConnection.prepareStatement(
                "SELECT last_insert_rowid();"
        );

        ResultSet resultSet = getIdStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return -1;
    }

    public void close() throws SQLException {
        dbConnection.close();
    }
}
