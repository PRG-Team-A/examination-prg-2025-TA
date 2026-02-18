package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.products.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// FIX #9: Removed unused import: org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

class DatabaseOperationsTest {
    private List<Product> testProducts;
    private static File dbFile = new File("database.db");
    private static File testDbFile = new File("test_database.db");

    @BeforeAll
    static void beforeAll() throws SQLException, IOException {
        setupTestDB();
    }

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseOperations.getInstance().nukeAllProducts();

        testProducts = new ArrayList<>();

        testProducts.add(new GroceryProduct("Test product 1"));
        testProducts.add(new GroceryProduct("Test product 2"));
        testProducts.add(new GroceryProduct("Test product 3"));
    }

    private static void copyFile(File sourceFile, File destinationFile) throws IOException {
        if (!destinationFile.exists()) {
            if (!destinationFile.createNewFile()) throw new IOException("Could not create destinationFile file.");
        }

        try (FileChannel source = new FileInputStream(sourceFile).getChannel()) {
            try (FileChannel destination = new FileOutputStream(destinationFile).getChannel()) {
                destination.transferFrom(source, 0, source.size());
            }
        }
    }

    @AfterAll
    static void afterAll() throws SQLException {
        DatabaseOperations.getInstance().close();
        if (!testDbFile.delete())
            System.out.println("Test DB could not be deleted!");
    }

    private static void setupTestDB() throws IOException, SQLException {
        copyFile(dbFile, testDbFile);

        SQLiteConnection.dbURL = "jdbc:sqlite:test_database.db";
        DatabaseSetup.setup(SQLiteConnection.getInstance());
    }

    @Test
    void getInstance() throws SQLException {
        DatabaseOperations instance = DatabaseOperations.getInstance();
        DatabaseOperations instance2 = DatabaseOperations.getInstance();
        assertEquals(instance.getClass(), instance2.getClass());
    }

    @Test
    void insertNewProduct() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();
        operations.insertNewProduct(testProducts.get(0));
    }

    // FIX #6: Fixed assertion — was comparing testProduct to itself, now compares to returnedProduct
    @Test
    void getProductByUUID() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        Product testProduct = testProducts.get(0);

        operations.insertNewProduct(testProduct);
        Product returnedProduct = operations.getProductByUUID(testProduct.getUuid());

        assertNotNull(returnedProduct, "Returned product should not be null");
        assertEquals(testProduct.getUuid(), returnedProduct.getUuid());
        assertEquals(testProduct.getDisplayName(), returnedProduct.getDisplayName());
    }

    @Test
    void deleteProducts() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        Product testProduct = testProducts.get(0);

        operations.insertNewProduct(testProduct);

        operations.deleteProducts(Collections.singletonList(testProduct.getUuid()));

        Product groceryProduct = operations.getProductByUUID(testProduct.getUuid());

        assertNull(groceryProduct);
    }

    @Test
    void getAllProducts() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        operations.insertMultipleProducts(testProducts);

        List<Product> products = operations.getAllProducts();

        boolean allProductsInDB = products.size() >= testProducts.size();

        assertTrue(allProductsInDB);
    }

    @Test
    void insertAndRetrieveElectronicProduct() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        ElectronicProduct ep = new ElectronicProduct("Test Laptop", 999.99, TaxCategory.STANDARD, true, 3);

        operations.insertNewProduct(ep);
        Product returnedProduct = operations.getProductByUUID(ep.getUuid());

        assertNotNull(returnedProduct, "Returned electronic product should not be null");
        assertInstanceOf(ElectronicProduct.class, returnedProduct);

        ElectronicProduct returnedEp = (ElectronicProduct) returnedProduct;
        assertEquals(ep.getUuid(), returnedEp.getUuid());
        assertEquals(ep.getDisplayName(), returnedEp.getDisplayName());
        assertEquals(ep.getPrice(), returnedEp.getPrice(), 0.01);
        assertEquals(ep.getWarrantyYears(), returnedEp.getWarrantyYears());
        assertTrue(returnedEp.isPremium());
        assertEquals(TaxCategory.STANDARD, returnedEp.getTaxCategory());
    }

    // FIX #7: New test — insert and retrieve NonGroceryProduct
    @Test
    void insertAndRetrieveNonGroceryProduct() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        NonGroceryProduct ngp = new NonGroceryProduct("Test Shirt", 29.99, TaxCategory.REDUCED, false);

        operations.insertNewProduct(ngp);
        Product returnedProduct = operations.getProductByUUID(ngp.getUuid());

        assertNotNull(returnedProduct, "Returned non-grocery product should not be null");
        assertInstanceOf(NonGroceryProduct.class, returnedProduct);

        NonGroceryProduct returnedNgp = (NonGroceryProduct) returnedProduct;
        assertEquals(ngp.getUuid(), returnedNgp.getUuid());
        assertEquals(ngp.getDisplayName(), returnedNgp.getDisplayName());
        assertEquals(ngp.getPrice(), returnedNgp.getPrice(), 0.01);
        assertFalse(returnedNgp.isPremium());
        assertEquals(TaxCategory.REDUCED, returnedNgp.getTaxCategory());
    }

    @Test
    void insertAndRetrieveWeightBasedProduct() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        WeightBasedProduct wbp = new WeightBasedProduct("Test Apples", 3.50);

        operations.insertNewProduct(wbp);
        Product returnedProduct = operations.getProductByUUID(wbp.getUuid());

        assertNotNull(returnedProduct, "Returned weight-based product should not be null");
        assertInstanceOf(WeightBasedProduct.class, returnedProduct);

        WeightBasedProduct returnedWbp = (WeightBasedProduct) returnedProduct;
        assertEquals(wbp.getUuid(), returnedWbp.getUuid());
        assertEquals(wbp.getDisplayName(), returnedWbp.getDisplayName());
        assertEquals(wbp.getPricePerKg(), returnedWbp.getPricePerKg(), 0.01);
    }

    @Test
    void getAllProductsMixedTypes() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        List<Product> mixedProducts = new ArrayList<>();
        mixedProducts.add(new GroceryProduct("Milk", 1.99));
        mixedProducts.add(new ElectronicProduct("Phone", 599.99));
        mixedProducts.add(new NonGroceryProduct("Hat", 15.00));
        mixedProducts.add(new WeightBasedProduct("Bananas", 2.50));

        operations.insertMultipleProducts(mixedProducts);

        List<Product> allProducts = operations.getAllProducts();

        assertTrue(allProducts.size() >= mixedProducts.size(),
                "Should have at least " + mixedProducts.size() + " products, got " + allProducts.size());
    }

    @Test
    void getProductByUUIDNotFound() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        Product result = operations.getProductByUUID(UUID.randomUUID());

        assertNull(result, "Looking up a non-existent UUID should return null");
    }
}