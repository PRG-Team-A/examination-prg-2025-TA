package com.prg2025ta.project.examinationpgr2025ta;

import com.prg2025ta.project.examinationpgr2025ta.exceptions.OutOfStockException;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

        amountOfProductInWarehouse = warehouse.getProductsInStock().getOrDefault(unknownProduct, 0);
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
        warehouse.acceptDelivery(testProduct, testProduct);
        warehouse.acceptDelivery(expiredProduct);

        warehouse.purgeExpiredProducts(LocalDate.ofYearDay(2026, 8));

        assertEquals(2, warehouse.getAmountOfProductInStock(testProduct));
        assertEquals(0, warehouse.getAmountOfProductInStock(expiredProduct));
    }

    @Test
    void removeFromWarehouse() {
        warehouse.acceptDelivery(testProduct, testProduct, expiredProduct);

        warehouse.removeFromWarehouse(testProduct, 2);

        assertEquals(0, warehouse.getAmountOfProductInStock(testProduct));
        assertEquals(1, warehouse.getAmountOfProductInStock(expiredProduct));

        warehouse.removeFromWarehouse(expiredProduct);
        assertEquals(0, warehouse.getAmountOfProductInStock(expiredProduct));


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
    }
}