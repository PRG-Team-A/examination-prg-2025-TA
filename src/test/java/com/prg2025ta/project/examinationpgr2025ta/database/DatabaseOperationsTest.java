package com.prg2025ta.project.examinationpgr2025ta.database;

import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// TODO: Implement test for the database access
class DatabaseOperationsTest {
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        testProducts = new ArrayList<>();

        testProducts.add(new GroceryProduct("Test product 1"));
        testProducts.add(new GroceryProduct("Test product 2"));
        testProducts.add(new GroceryProduct("Test product 3"));
    }


    @Test
    void getInstance() throws SQLException {
        DatabaseOperations instance = DatabaseOperations.getInstance();
        DatabaseOperations instance2 = DatabaseOperations.getInstance();
        assertEquals(instance.getClass(), instance2.getClass());
    }

    @Test
    void insertNewProduct() {

    }

    @Test
    void insertMultipleProducts() {
    }
}