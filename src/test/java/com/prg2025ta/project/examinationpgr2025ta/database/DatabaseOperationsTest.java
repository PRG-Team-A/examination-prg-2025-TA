package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// TODO: Implement test for the database access
class DatabaseOperationsTest {
    private List<Product> testProducts;
    private static File dbFile = new File("database.db");
    private static File testDbFile = new File("test_database.db");

    @BeforeAll
    static void beforeAll() throws SQLException, IOException {
        setupTestDB();
    }

    @BeforeEach
    void setUp() {

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
    static void afterAll() {
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
        operations.insertNewProduct(testProducts.getFirst());
    }

    @Test
    void getProductByUUID() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        Product testProduct = testProducts.getFirst();

        operations.insertNewProduct(testProduct);
        GroceryProduct returnedProduct = operations.getProductByUUID(testProduct.getUuid());

        assertEquals(testProduct.getUuid(), testProduct.getUuid());
    }

    @Test
    void deleteProducts() throws SQLException {
        DatabaseOperations operations = DatabaseOperations.getInstance();

        Product testProduct = testProducts.getFirst();

        operations.insertNewProduct(testProduct);

        operations.deleteProducts(Collections.singletonList(testProduct.getUuid()));

        GroceryProduct groceryProduct = operations.getProductByUUID(testProduct.getUuid());

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
}