package com.prg2025ta.project.examinationpgr2025ta.products;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Non-grocery product type with tax category, premium flag (for loyalty discounts),
 * and optional sale/availability window.
 */
public class NonGroceryProduct extends Product {
    public static final double DEFAULT_PREMIUM_DISCOUNT = 0.10; // 10% discount for premium customers

    // CHANGED: Removed equals(Product other) overload so this class relies on Product.equals(Object)/hashCode().

    private TaxCategory taxCategory;
    private boolean isPremium;
    private LocalDate saleStartDate; // nullable - when product becomes available on sale
    private LocalDate saleEndDate;   // nullable - when sale ends

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

    /**
     * Checks if the product is available (in terms of sale/availability window) on the provided date.
     * If no window is set, returns true.
     */
    public boolean isAvailable(LocalDate date) {
        if (date == null) return true;
        if (saleStartDate != null && date.isBefore(saleStartDate))
            return false;
        if (saleEndDate != null && date.isAfter(saleEndDate))
            return false;
        return true;
    }

    /**
     * Calculates price including tax (based on the product's TaxCategory).
     * If base price is NaN, returns Double.NaN.
     */
    public double getPriceWithTax() {
        double basePrice = getPrice();
        if (Double.isNaN(basePrice))
            return Double.NaN;
        return basePrice * (1.0 + (taxCategory == null ? TaxCategory.STANDARD.getRate() : taxCategory.getRate()));
    }

    /**
     * Returns the effective price a customer should pay considering premium discount.
     * If premiumCustomer==true and the product is marked premium, a discount is applied to the base price.
     */
    public double getPriceForCustomer(boolean premiumCustomer) {
        double base = getPrice();
        if (Double.isNaN(base)) return Double.NaN;
        if (premiumCustomer && this.isPremium) {
            return base * (1.0 - DEFAULT_PREMIUM_DISCOUNT);
        }
        return base;
    }
}
