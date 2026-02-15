package com.prg2025ta.project.examinationpgr2025ta.products;

import java.util.UUID;


public class NonGroceryProduct extends Product {
    public static final double DEFAULT_PREMIUM_DISCOUNT = 0.10; // 10% discount for premium customers

    private TaxCategory taxCategory;
    private boolean isPremium;

    public NonGroceryProduct(String displayName) {
        this(displayName, Product.defaultPrice);
    }

    public NonGroceryProduct(String displayName, double price) {
        final boolean isPremium = false;

        this(displayName, price, TaxCategory.STANDARD, isPremium);
    }

    public NonGroceryProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium) {
        this(displayName, price, taxCategory, isPremium, UUID.randomUUID());
    }

    public NonGroceryProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium, UUID uuid) {
        super(displayName, price, uuid);
        this.taxCategory = taxCategory;
        this.isPremium = isPremium;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    //calculate tax depends on its category
    public double getPriceWithTax() {
        double basePrice = getPrice();
        if (Double.isNaN(basePrice))
            return Double.NaN;
        if (taxCategory == null) taxCategory = TaxCategory.STANDARD;
        return basePrice * (1.0 + taxCategory.getRate());
    }
}
