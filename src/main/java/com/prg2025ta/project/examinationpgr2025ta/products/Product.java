package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;
import java.util.Objects;

public class Product {
    public static final double defaultPrice = Double.NaN;

    // CHANGED: Made uuid final and implemented equals(Object)/hashCode() based on uuid; removed setUuid(...) to keep identity stable.

    private final UUID uuid;
    private double price;
    private String displayName;

    public Product(String displayName, double price, UUID uuid) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.displayName = displayName;
        this.price = price;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public double getPrice() {
        return this.price;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    // setUuid removed intentionally to keep identity immutable
    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product other)) return false;
        return this.uuid.equals(other.uuid);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }
}