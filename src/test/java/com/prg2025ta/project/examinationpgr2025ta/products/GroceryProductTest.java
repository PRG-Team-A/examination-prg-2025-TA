package com.prg2025ta.project.examinationpgr2025ta.products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GroceryProductTest {
    GroceryProduct groceryProduct;

    @BeforeEach
    void setup() {
        groceryProduct = new GroceryProduct("grocery", 20, LocalDate.MIN);
    }

    @Test
    void testConstructor() {
        GroceryProduct product1 = new GroceryProduct("test");
        GroceryProduct product2 = new GroceryProduct(
                "test",
                GroceryProduct.defaultPrice
        );
        GroceryProduct product3 = new GroceryProduct(
                "test",
                GroceryProduct.defaultPrice,
                GroceryProduct.defaultDateOfExpiry
        );
        GroceryProduct product4 = new GroceryProduct(
                "test",
                GroceryProduct.defaultPrice,
                GroceryProduct.defaultDateOfExpiry,
                GroceryProduct.defaultNeedsCooling
        );
    }

    @Test
    void getDateOfExpiry() throws NoSuchFieldException, IllegalAccessException {
        Field dateOfExpiryField = groceryProduct.getClass().getDeclaredField("dateOfExpiry");
        dateOfExpiryField.setAccessible(true);
        LocalDate expectedDateOfExpiry = (LocalDate) dateOfExpiryField.get(groceryProduct);
        assertEquals(expectedDateOfExpiry, groceryProduct.getDateOfExpiry());
    }

    @Test
    void needsToBeCooled() throws NoSuchFieldException, IllegalAccessException {
        Field needsCoolingField = groceryProduct.getClass().getDeclaredField("needsCooling");
        needsCoolingField.setAccessible(true);
        Boolean expectedNeedsCooling = needsCoolingField.getBoolean(groceryProduct);
        assertEquals(expectedNeedsCooling, groceryProduct.needsToBeCooled());
    }
}