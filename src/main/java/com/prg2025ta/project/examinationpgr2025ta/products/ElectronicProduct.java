package com.prg2025ta.project.examinationpgr2025ta.products;

import java.time.LocalDate;
import java.util.UUID;

public class ElectronicProduct extends NonGroceryProduct {
    private int warrantyMonths; // typical warranty length in months

    public ElectronicProduct(String displayName, double price) {
        this(displayName, price, TaxCategory.STANDARD, false, null, null, 12);
    }

    public ElectronicProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium, LocalDate saleStartDate, LocalDate saleEndDate, int warrantyMonths) {
        this(displayName, price, taxCategory, isPremium, saleStartDate, saleEndDate, warrantyMonths, java.util.UUID.randomUUID());
    }

    public ElectronicProduct(String displayName, double price, TaxCategory taxCategory, boolean isPremium, LocalDate saleStartDate, LocalDate saleEndDate, int warrantyMonths, UUID uuid) {
        super(displayName, price, taxCategory, isPremium, saleStartDate, saleEndDate, uuid);
        this.warrantyMonths = warrantyMonths;
    }

    public int getWarrantyMonths() { return warrantyMonths; }
    public void setWarrantyMonths(int warrantyMonths) {
        this.warrantyMonths = warrantyMonths;
    }
}