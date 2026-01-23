package com.prg2025ta.project.examinationpgr2025ta.products;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    Product testProduct;

    @BeforeEach
    void setup() {
        testProduct = new Product("TestProduct", 12.1, UUID.randomUUID());
    }

    @Test
    void getUuid() throws NoSuchFieldException, IllegalAccessException {
        final Field uuidField = testProduct.getClass().getDeclaredField("uuid");
        uuidField.setAccessible(true);
        UUID expectedUUID = (UUID) uuidField.get(testProduct);
        assertEquals(expectedUUID, testProduct.getUuid());
    }

    @Test
    void getPrice() throws NoSuchFieldException, IllegalAccessException {
        final Field priceField = testProduct.getClass().getDeclaredField("price");
        priceField.setAccessible(true);
        double expectedPrice = priceField.getDouble(testProduct);
        assertEquals(expectedPrice, testProduct.getPrice());
    }

    @Test
    void getDisplayName() throws NoSuchFieldException, IllegalAccessException {
        final Field displayNameField = testProduct.getClass().getDeclaredField("displayName");
        displayNameField.setAccessible(true);
        String expectedDisplayName = (String) displayNameField.get(testProduct);
        assertEquals(expectedDisplayName, testProduct.getDisplayName());
    }

    @Test
    void setUuid() {
        UUID expectedUUID = UUID.randomUUID();

        testProduct.setUuid(expectedUUID);

        assertEquals(expectedUUID, testProduct.getUuid());
    }

    @Test
    void setPrice() {
        Double expectedPrice = 7.5;
        testProduct.setPrice(expectedPrice);
        assertEquals(expectedPrice, testProduct.getPrice());
    }

    @Test
    void setDisplayName() {
        String expectedDisplayName = "argh";
        testProduct.setDisplayName(expectedDisplayName);
        assertEquals(expectedDisplayName, testProduct.getDisplayName());
    }

    @Test
    void testEquals() {
        assertTrue(testProduct.equals(testProduct));
    }
}