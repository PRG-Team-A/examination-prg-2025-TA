package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;

/**
 * Product sold by weight (e.g. items on a scale). The pricePerKg is stored separately
 * and can be used to calculate the price for a measured weight.
 *
 * Note: Product.price is left as Product.defaultPrice (NaN) because final price depends on measured weight.
 */
public class WeightBasedProduct extends Product {
    private double pricePerKg;

    // CHANGED: Removed equals(Product other) overload so equality is consistent (Product.equals(Object)/hashCode()).

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