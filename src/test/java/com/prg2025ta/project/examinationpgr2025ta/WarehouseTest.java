package com.prg2025ta.project.examinationpgr2025ta;

import com.prg2025ta.project.examinationpgr2025ta.exceptions.OutOfStockException;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseTest {
    private Warehouse warehouse;
    GroceryProduct testProduct = new GroceryProduct("Test product", 5);
    GroceryProduct unknownProduct = new GroceryProduct("Not in warehouse", 15);
    GroceryProduct expiredProduct = new GroceryProduct(
            "Expired",
            15,
            LocalDate.MIN
    );

    NonGroceryProduct nonGroceryProduct = new NonGroceryProduct();

    @BeforeEach
    void setup() {
        warehouse = new Warehouse();
    }

    @AfterEach
    void teardown() {
        warehouse = null;
    }

    @Test
    void acceptDelivery() {

        warehouse.acceptDelivery(testProduct);

        int amountOfProductInWarehouse = warehouse.getProductsInStock().get(testProduct);
        assertEquals(1, amountOfProductInWarehouse);

        warehouse.acceptDelivery(testProduct);

        amountOfProductInWarehouse = warehouse.getProductsInStock().get(testProduct);
        assertEquals(2, amountOfProductInWarehouse);

        try {
            amountOfProductInWarehouse = warehouse.getProductsInStock().get(unknownProduct);
        } catch (NullPointerException e) {
            amountOfProductInWarehouse = 0;
        }
        assertEquals(0, amountOfProductInWarehouse);
    }

    @Test
    void getAmountOfProductInStock() {
        warehouse.acceptDelivery(testProduct, testProduct);
        assertEquals(2, warehouse.getAmountOfProductInStock(testProduct));
        assertEquals(0, warehouse.getAmountOfProductInStock(unknownProduct));
    }

    @Test
    void purgeExpiredProducts() {
        warehouse.acceptDelivery(testProduct, expiredProduct);
        warehouse.purgeExpiredProducts();

        assertEquals(0, warehouse.getAmountOfProductInStock(expiredProduct));
        assertEquals(1, warehouse.getAmountOfProductInStock(testProduct));
    }

    @Test
    void testPurgeExpiredProducts() {
        warehouse.acceptDelivery(testProduct, testProduct, expiredProduct, nonGroceryProduct);
        warehouse.purgeExpiredProducts(LocalDate.ofYearDay(2026, 8));

        assertEquals(2, warehouse.getAmountOfProductInStock(testProduct));
        assertEquals(0, warehouse.getAmountOfProductInStock(expiredProduct));
        assertEquals(1, warehouse.getAmountOfProductInStock(nonGroceryProduct));
    }

    @Test
    void removeFromWarehouse() {
        warehouse.acceptDelivery(testProduct, testProduct, expiredProduct, nonGroceryProduct, nonGroceryProduct);
        warehouse.removeFromWarehouse(testProduct, 2);
        assertEquals(0, warehouse.getAmountOfProductInStock(testProduct));
        assertEquals(1, warehouse.getAmountOfProductInStock(expiredProduct));

        warehouse.removeFromWarehouse(expiredProduct);
        assertEquals(0, warehouse.getAmountOfProductInStock(expiredProduct));

        warehouse.removeFromWarehouse(nonGroceryProduct, 1);
        assertEquals(1, warehouse.getAmountOfProductInStock(nonGroceryProduct));

        Exception exception = assertThrows(OutOfStockException.class, () -> {
            warehouse.removeFromWarehouse(expiredProduct);
        });

        assertNotNull(exception);
    }

    @Test
    void testConstructor() {
        Warehouse warehouse1 = new Warehouse(testProduct, testProduct, expiredProduct);
        assertEquals(2, warehouse1.getAmountOfProductInStock(testProduct));
        assertEquals(1, warehouse1.getAmountOfProductInStock(expiredProduct));
        assertEquals(0, warehouse1.getAmountOfProductInStock(nonGroceryProduct));
    }

    static class NonGroceryProduct implements Product {

        @Override
        public UUID getUuid() {
            return UUID.randomUUID();
        }

        @Override
        public double getPrice() {
            return 0;
        }

        @Override
        public String getDisplayName() {
            return "NonGroceryProduct";
        }

        @Override
        public boolean equals(Product other) {
            return other.getUuid().equals(this.getUuid());
        }
    }
}