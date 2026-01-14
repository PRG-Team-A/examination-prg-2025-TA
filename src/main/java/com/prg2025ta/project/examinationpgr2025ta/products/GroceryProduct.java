package com.prg2025ta.project.examinationpgr2025ta.products;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class GroceryProduct implements Product {
    public static final double defaultPrice = Double.NaN;
    public static final LocalDate defaultDateOfExpiry = LocalDate.MAX;
    public static final boolean defaultNeedsCooling = false;

    private UUID uuid;
    private double price;
    private String displayName;
    private LocalDate dateOfExpiry;
    private boolean needsCooling;

    @Override public UUID getUuid() { return uuid; }
    @Override public double getPrice() { return price; }
    @Override public String getDisplayName() { return displayName; }

    public LocalDate getDateOfExpiry() { return dateOfExpiry; }
    public boolean needsToBeCooled() { return needsCooling; }

    public void setPrice(double newPrice) { this.price = newPrice; }
    public void setDisplayName(String newDisplayName) { this.displayName = newDisplayName; }

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
        this.uuid = uuid;
        this.price = price;
        this.displayName = displayName;
        this.dateOfExpiry = dateOfExpiry;
        this.needsCooling = needsCooling;
    }
}
