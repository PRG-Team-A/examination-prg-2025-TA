package com.prg2025ta.project.examinationpgr2025ta.api.models;

public class ProductModel {
    private String displayName;
    private double price;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductModel(String displayName, double price) {
        this.displayName = displayName;
        this.price = price;
    }
}
