package com.prg2025ta.project.examinationpgr2025ta.products;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class GroceryProduct extends Product {
    public static final LocalDate defaultDateOfExpiry = LocalDate.MAX;
    public static final boolean defaultNeedsCooling = false;

    private LocalDate dateOfExpiry;
    private boolean needsCooling;

    public LocalDate getDateOfExpiry() { return dateOfExpiry; }
    public boolean needsToBeCooled() { return needsCooling; }

    public GroceryProduct(String displayName) {
        this(displayName, GroceryProduct.defaultPrice);
    }
    public GroceryProduct(String displayName, double price) {
        this(displayName, price, GroceryProduct.defaultDateOfExpiry);
    }
    public GroceryProduct(String displayName, double price, LocalDate dateOfExpiry) {
        this(displayName, price, dateOfExpiry, GroceryProduct.defaultNeedsCooling);
    }
    public GroceryProduct(String displayName, double price, LocalDate dateOfExpiry, boolean needsCooling) {
        this(displayName, price, dateOfExpiry, needsCooling, UUID.randomUUID());
    }
    public GroceryProduct(String displayName, double price, LocalDate dateOfExpiry, boolean needsCooling, UUID uuid) {
        super(displayName, price, uuid);
        this.dateOfExpiry = dateOfExpiry;
        this.needsCooling = needsCooling;
    }
}