package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;


public class WeightBasedProduct extends Product {
    private double pricePerKg;

    public WeightBasedProduct(String displayName, double pricePerKg) {
        this(displayName, pricePerKg, UUID.randomUUID());
    }

    public WeightBasedProduct(String displayName, double pricePerKg, UUID uuid) {
        super(displayName, Product.defaultPrice, uuid);
        this.pricePerKg = pricePerKg;
    }

    public double getPricePerKg() { return pricePerKg; }
    public void setPricePerKg(double pricePerKg) { this.pricePerKg = pricePerKg; }

    public double calculatePrice(double weightKg) {
        if (weightKg < 0) throw new IllegalArgumentException("weightKg must be >= 0");
        return pricePerKg * weightKg;
    }
}