package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;

public class Product {
    public static final double defaultPrice = Double.NaN;

    private UUID uuid;
    private double price;
    private String displayName;

    public Product(String displayName, double price, UUID uuid) {
        this.uuid = uuid;
        this.displayName = displayName;
        this.price = price;
    }

    public UUID getUuid() { return this.uuid; }
    public double getPrice() { return this.price; }
    public String getDisplayName() { return this.displayName; }

    public void setUuid(UUID uuid) { this.uuid = uuid; }
    public void setPrice(double newPrice) { this.price = newPrice; }
    public void setDisplayName(String displayName) { this.displayName = displayName; }

    public boolean equals(Product other) { return this.uuid.equals(other.getUuid()); }
}
