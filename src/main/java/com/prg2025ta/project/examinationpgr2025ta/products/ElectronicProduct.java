package com.prg2025ta.project.examinationpgr2025ta.products;

import java.time.LocalDate;
import java.util.UUID;

public class ElectronicProduct extends NonGroceryProduct {
    private int warrantyYears; // typical warranty length in years

    public ElectronicProduct(String displayName, double price) {
        this(displayName, price, TaxCategory.STANDARD, false, 2);
    }

    public ElectronicProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium, int warrantyYears) {
        this(displayName, price, taxCategory, isPremium, warrantyYears, java.util.UUID.randomUUID());
    }

    public ElectronicProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium, int warrantyYears, UUID uuid) {
        super(displayName, price, taxCategory, isPremium,uuid);
        this.warrantyYears = warrantyYears;
    }

    public int getWarrantyYears() { return warrantyYears; }
    public void setWarrantyYears(int warrantyYears) {
        this.warrantyYears = warrantyYears;
    }
}