package com.prg2025ta.project.examinationpgr2025ta.products;

import java.time.LocalDate;
import java.util.UUID;


public class NonGroceryProduct extends Product {
    public static final double DEFAULT_PREMIUM_DISCOUNT = 0.10; // 10% discount for premium customers

    private TaxCategory taxCategory;
    private boolean isPremium;
    private LocalDate saleStartDate; //count when product becomes available on sale
    private LocalDate saleEndDate;   //count when sale ends

    public NonGroceryProduct(String displayName) {
        this(displayName, Product.defaultPrice);
    }

    public NonGroceryProduct(String displayName, double price) {
        this(displayName, price, TaxCategory.STANDARD, false, null, null);
    }

    public NonGroceryProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium,
                             LocalDate saleStartDate, LocalDate saleEndDate) {
        this(displayName, price, taxCategory, isPremium, saleStartDate, saleEndDate, UUID.randomUUID());
    }

    public NonGroceryProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium,
                             LocalDate saleStartDate, LocalDate saleEndDate, UUID uuid) {
        super(displayName, price, uuid);
        this.taxCategory = taxCategory;
        this.isPremium = isPremium;
        this.saleStartDate = saleStartDate;
        this.saleEndDate = saleEndDate;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public LocalDate getSaleStartDate() {
        return saleStartDate;
    }

    public LocalDate getSaleEndDate() {
        return saleEndDate;
    }

    public void setTaxCategory(TaxCategory taxCategory) {
        this.taxCategory = taxCategory;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public void setSaleStartDate(LocalDate saleStartDate) {
        this.saleStartDate = saleStartDate;
    }

    public void setSaleEndDate(LocalDate saleEndDate) {
        this.saleEndDate = saleEndDate;
    }

    //check product available/not
    public boolean isAvailable(LocalDate date) {
        if (date == null) return true;
        if (saleStartDate != null && date.isBefore(saleStartDate))
            return false;
        if (saleEndDate != null && date.isAfter(saleEndDate))
            return false;
        return true;
    }

    //calculate tax depends on its category
    public double getPriceWithTax() {
        double basePrice = getPrice();
        if (Double.isNaN(basePrice))
            return Double.NaN;
        return basePrice * (1.0 + (taxCategory == null ? TaxCategory.STANDARD.getRate() : taxCategory.getRate()));
    }

    //Returns the final price, applying a premium discount when eligible
    public double getPriceForCustomer(boolean premiumCustomer) {
        double base = getPrice();
        if (Double.isNaN(base)) return Double.NaN;
        if (premiumCustomer && this.isPremium) {
            return base * (1.0 - DEFAULT_PREMIUM_DISCOUNT);
        }
        return base;
    }
}
