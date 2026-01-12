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
    void getUuid() throws NoSuchFieldException, IllegalAccessException {
        Field uuidField = groceryProduct.getClass().getDeclaredField("uuid");
        uuidField.setAccessible(true);
        UUID expectedUUID = (UUID) uuidField.get(groceryProduct);

        assertEquals(expectedUUID, groceryProduct.getUuid());
    }

    @Test
    void getPrice() throws NoSuchFieldException, IllegalAccessException {
        Field priceField = groceryProduct.getClass().getDeclaredField("price");
        priceField.setAccessible(true);
        double expectedPrice = priceField.getDouble(groceryProduct);
        assertEquals(expectedPrice, groceryProduct.getPrice());
    }

    @Test
    void getDisplayName() throws NoSuchFieldException, IllegalAccessException {
        Field displayNameField = groceryProduct.getClass().getDeclaredField("displayName");
        displayNameField.setAccessible(true);
        String expectedDisplayName = (String) displayNameField.get(groceryProduct);
        assertEquals(expectedDisplayName, groceryProduct.getDisplayName());
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

    @Test
    void setPrice() {
        double expectedPrice = 500;
        groceryProduct.setPrice(expectedPrice);
        assertEquals(expectedPrice, groceryProduct.getPrice());
    }

    @Test
    void setDisplayName() {
        String expectedDisplayName = "TestDisplayName";
        groceryProduct.setDisplayName(expectedDisplayName);
        assertEquals(expectedDisplayName, groceryProduct.getDisplayName());
    }
}