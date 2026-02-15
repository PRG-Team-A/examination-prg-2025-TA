package com.prg2025ta.project.examinationpgr2025ta.products;


public enum TaxCategory {
    REDUCED(0.05),   // e.g. reduced tax rate (5%)
    STANDARD(0.21);  // e.g. standard tax rate (21%)

    private final double rate;

    TaxCategory(double rate) {
        this.rate = rate;
    }

    //tax rate as fraction (e.g. 0.21 for 21%)
    public double getRate() {
        return rate;
    }
}