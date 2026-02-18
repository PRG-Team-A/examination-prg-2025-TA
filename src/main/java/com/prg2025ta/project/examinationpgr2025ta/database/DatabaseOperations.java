package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.products.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseOperations {
    private static final Logger log = LogManager.getLogger(DatabaseOperations.class);
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
        try (PreparedStatement prepared = dbConnection.prepareStatement(
                "INSERT INTO products (product_uuid, product_type, display_name, price, price_per_kg, needs_cooling, expiry_date, tax_category, is_premium, warranty_years) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);")) {
            for (Product product : products) {
                prepared.setString(1, product.getUuid().toString());

                if (product instanceof GroceryProduct gp) {
                    insertGroceryProduct(prepared, gp);
                } else if (product instanceof ElectronicProduct ep) {
                    insertElectronicProduct(prepared, ep);
                } else if (product instanceof NonGroceryProduct ngp) {
                    insertNonGroceryProduct(prepared, ngp);
                } else if (product instanceof WeightBasedProduct wbp) {
                    insertWeightBasedProduct(prepared, wbp);
                } else {
                    insertUnknownProduct(prepared, product);
                }

                prepared.addBatch();
            }

            int[] results = prepared.executeBatch();
            dbConnection.commit();
            dbConnection.setAutoCommit(true);

            System.out.println("Inserted " + results.length + " products.");
        } catch (SQLException sqlException) {
            dbConnection.rollback();
            dbConnection.setAutoCommit(true);
            throw sqlException;
        }
    }

    private void insertGroceryProduct(PreparedStatement prepared, GroceryProduct gp) throws SQLException {
        prepared.setString(2, "grocery");
        prepared.setString(3, gp.getDisplayName());
        prepared.setDouble(4, gp.getPrice());
        prepared.setNull(5, Types.NUMERIC);
        prepared.setInt(6, gp.needsToBeCooled() ? 2 : 1);
        prepared.setLong(7, gp.getDateOfExpiry().atStartOfDay(ZoneId.of("UTC")).toEpochSecond());
        prepared.setString(8, "STANDARD");
        prepared.setInt(9, 0);
        prepared.setInt(10, 0);
    }

    private void insertElectronicProduct(PreparedStatement prepared, ElectronicProduct ep) throws SQLException {
        prepared.setString(2, "electronic");
        prepared.setString(3, ep.getDisplayName());
        prepared.setDouble(4, ep.getPrice());
        prepared.setNull(5, Types.NUMERIC);
        prepared.setInt(6, 0);
        prepared.setLong(7, 0);
        prepared.setString(8, ep.getTaxCategory() != null ? ep.getTaxCategory().name() : "STANDARD");
        prepared.setInt(9, ep.isPremium() ? 1 : 0);
        prepared.setInt(10, ep.getWarrantyYears());
    }

    private void insertNonGroceryProduct(PreparedStatement prepared, NonGroceryProduct ngp) throws SQLException {
        prepared.setString(2, "non_grocery");
        prepared.setString(3, ngp.getDisplayName());
        prepared.setDouble(4, ngp.getPrice());
        prepared.setNull(5, Types.NUMERIC);
        prepared.setInt(6, 0);
        prepared.setLong(7, 0);
        prepared.setString(8, ngp.getTaxCategory() != null ? ngp.getTaxCategory().name() : "STANDARD");
        prepared.setInt(9, ngp.isPremium() ? 1 : 0);
        prepared.setInt(10, 0);
    }

    private void insertWeightBasedProduct(PreparedStatement prepared, WeightBasedProduct wbp) throws SQLException {
        prepared.setString(2, "weight_based");
        prepared.setString(3, wbp.getDisplayName());
        prepared.setNull(4, Types.NUMERIC);
        prepared.setDouble(5, wbp.getPricePerKg());
        prepared.setInt(6, 0);
        prepared.setLong(7, 0);
        prepared.setString(8, "STANDARD");
        prepared.setInt(9, 0);
        prepared.setInt(10, 0);
    }

    private void insertUnknownProduct(PreparedStatement prepared, Product product) throws SQLException {
        prepared.setString(2, "unknown");
        prepared.setString(3, product.getDisplayName());
        prepared.setDouble(4, product.getPrice());
        prepared.setNull(5, Types.NUMERIC);
        prepared.setInt(6, 0);
        prepared.setLong(7, 0);
        prepared.setString(8, "STANDARD");
        prepared.setInt(9, 0);
        prepared.setInt(10, 0);
    }

    public Product getProductByUUID(UUID uuid) throws SQLException {
        PreparedStatement statement = dbConnection
                .prepareStatement("SELECT product_uuid, product_type, display_name, price, price_per_kg, needs_cooling, expiry_date, tax_category, is_premium, warranty_years FROM products WHERE product_uuid = ?");

        statement.setString(1, uuid.toString());
        ResultSet resultSet = statement.executeQuery();

        if (!resultSet.next()) {
            return null;
        }

        String product_id = resultSet.getString("product_uuid");
        String product_type = resultSet.getString("product_type");
        String display_name = resultSet.getString("display_name");
        double price = resultSet.getDouble("price");
        double price_per_kg = resultSet.getDouble("price_per_kg");
        int needsCooling = resultSet.getInt("needs_cooling");
        long expiry_date = resultSet.getLong("expiry_date");
        String tax_category = resultSet.getString("tax_category");
        int is_premium = resultSet.getInt("is_premium");
        int warranty_years = resultSet.getInt("warranty_years");

        if (product_id == null || display_name == null) {
            return null;
        }

        try {
            return switch (product_type) {
                case "grocery" -> deserializeGroceryProduct(product_id, display_name, price, needsCooling, expiry_date);
                case "electronic" -> deserializeElectronicProduct(product_id, display_name, price, tax_category, is_premium, warranty_years);
                case "non_grocery" -> deserializeNonGroceryProduct(product_id, display_name, price, tax_category, is_premium);
                case "weight_based" -> deserializeWeightBasedProduct(product_id, display_name, price_per_kg);
                default -> {
                    log.warn("Unknown product type: " + product_type);
                    yield null;
                }
            };
        } catch (IllegalArgumentException e) {
            log.error("Error deserializing product with UUID: " + product_id, e);
            return null;
        }
    }

    private Product deserializeGroceryProduct(String product_id, String display_name, double price, int needsCooling, long expiry_date) {
        LocalDate dateOfExpiry = expiry_date > 0
                ? LocalDate.ofInstant(Instant.ofEpochSecond(expiry_date), ZoneId.of("UTC"))
                : GroceryProduct.defaultDateOfExpiry;
        boolean doesProductNeedCooling = needsCooling == 2;
        return new GroceryProduct(
                display_name,
                price,
                dateOfExpiry,
                doesProductNeedCooling,
                UUID.fromString(product_id)
        );
    }

    private Product deserializeElectronicProduct(String product_id, String display_name, double price, String tax_category, int is_premium, int warranty_years) {
        TaxCategory taxCat = TaxCategory.valueOf(tax_category != null ? tax_category : "STANDARD");
        boolean isPremium = is_premium == 1;
        return new ElectronicProduct(
                display_name,
                price,
                taxCat,
                isPremium,
                warranty_years,
                UUID.fromString(product_id)
        );
    }

    private Product deserializeNonGroceryProduct(String product_id, String display_name, double price, String tax_category, int is_premium) {
        TaxCategory taxCat = TaxCategory.valueOf(tax_category != null ? tax_category : "STANDARD");
        boolean isPremium = is_premium == 1;
        return new NonGroceryProduct(
                display_name,
                price,
                taxCat,
                isPremium,
                UUID.fromString(product_id)
        );
    }

    private Product deserializeWeightBasedProduct(String product_id, String display_name, double price_per_kg) {
        return new WeightBasedProduct(
                display_name,
                price_per_kg,
                UUID.fromString(product_id)
        );
    }

    public List<Product> getAllProducts() throws SQLException {
        List<Product> products = new ArrayList<>();

        Statement statement = dbConnection.createStatement();
        statement.execute("SELECT product_uuid, product_type, display_name, price, price_per_kg, needs_cooling, expiry_date, tax_category, is_premium, warranty_years FROM products;");

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            String uuid = resultSet.getString("product_uuid");
            String product_type = resultSet.getString("product_type");
            String display_name = resultSet.getString("display_name");
            double price = resultSet.getDouble("price");
            double price_per_kg = resultSet.getDouble("price_per_kg");
            int needsCooling = resultSet.getInt("needs_cooling");
            long expiry_date = resultSet.getLong("expiry_date");
            String tax_category = resultSet.getString("tax_category");
            int is_premium = resultSet.getInt("is_premium");
            int warranty_years = resultSet.getInt("warranty_years");

            if (uuid == null || display_name == null) {
                continue;
            }

            Product product = null;

            try {
                product = switch (product_type) {
                    case "grocery" -> deserializeGroceryProduct(uuid, display_name, price, needsCooling, expiry_date);
                    case "electronic" -> deserializeElectronicProduct(uuid, display_name, price, tax_category, is_premium, warranty_years);
                    case "non_grocery" -> deserializeNonGroceryProduct(uuid, display_name, price, tax_category, is_premium);
                    case "weight_based" -> deserializeWeightBasedProduct(uuid, display_name, price_per_kg);
                    default -> {
                        if (needsCooling != 0 || expiry_date != 0) {
                            yield deserializeGroceryProduct(uuid, display_name, price, needsCooling, expiry_date);
                        }
                        yield null;
                    }
                };
            } catch (IllegalArgumentException e) {
                log.error("Error deserializing product with UUID: " + uuid, e);
                continue;
            }

            if (product != null) {
                products.add(product);
            }
        }

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
        dbConnection.setAutoCommit(true);

        System.out.println("Deleted " + result.length + " products.");
    }

    public boolean insertSale(SalesClass sale) throws SQLException {
        dbConnection.setAutoCommit(false);
        PreparedStatement insertSaleStatement = dbConnection
                .prepareStatement("INSERT INTO sales (sale_id, customerId, paymentMethod, total) VALUES (?, ?, ?, ?)");

        PreparedStatement insertProductStatement = dbConnection
                .prepareStatement("INSERT INTO sales_products (sale_id, product_id) VALUES (?, ?)");

        PreparedStatement getHighestSaleId = dbConnection
                .prepareStatement("SELECT max(sale_id) FROM sales;");

        int highestSaleId = getHighestSaleId.executeQuery().getInt(1);
        int saleId = highestSaleId + 1;

        insertSaleStatement.setInt(1, saleId);
        insertSaleStatement.setInt(2, sale.getCustomerID());
        insertSaleStatement.setString(3, sale.getPaymentMethod());
        insertSaleStatement.setDouble(4, sale.getTotal());

        int rowCount = insertSaleStatement.executeUpdate();

        if (rowCount != 1) {
            log.warn("Error with database operation");
            dbConnection.rollback();
            dbConnection.setAutoCommit(true);
            return false;
        }

        insertProductStatement.setInt(1, saleId);

        for (Product productBought : sale.getProductsBought()) {
            UUID productUUID = productBought.getUuid();
            insertProductStatement.setString(2, productUUID.toString());
            insertProductStatement.addBatch();
        }
        insertProductStatement.executeBatch();

        dbConnection.commit();
        dbConnection.setAutoCommit(true);
        return true;
    }

    public List<SalesClass> getAllSales() throws SQLException {
        List<SalesClass> sales = new ArrayList<>();

        PreparedStatement selectSalesStatement = dbConnection
                .prepareStatement("SELECT sale_id, customerId, paymentMethod, total FROM sales;");

        ResultSet rs = selectSalesStatement.executeQuery();
        while (rs.next()) {
            int saleId = rs.getInt("sale_id");
            int customerId = rs.getInt("customerId");
            String paymentMethod = rs.getString("paymentMethod");
            double total = rs.getDouble("total");

            List<Product> products = getProductsFromSale(saleId);

            sales.add(new SalesClass(saleId, customerId, paymentMethod, products, total));
        }

        return sales;
    }

    private List<Product> getProductsFromSale(int saleId) throws SQLException {
        List<Product> products = new ArrayList<>();

        PreparedStatement statement = dbConnection
                .prepareStatement("SELECT product_id FROM sales_products WHERE sale_id = ?;");

        statement.setInt(1, saleId);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String productUUID = resultSet.getString("product_id");
            Product product = getProductByUUID(UUID.fromString(productUUID));
            if (product != null) {
                products.add(product);
            }
        }

        return products;
    }

    public void nukeAllProducts() throws SQLException {
        Statement nukeStatement = dbConnection.createStatement();
        nukeStatement.execute("DELETE FROM products;");
    }

    public void close() throws SQLException {
        dbConnection.close();
    }
}