package com.prg2025ta.project.examinationpgr2025ta.api.models;

public class ProductModel {
    private String displayName;
    private double price;
    private String productUUID;

    public String getDisplayName() {
        return displayName;
    }

    public String getProductUUID() { return productUUID; }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public ProductModel(String displayName, double price, String productUUID) {
        this.displayName = displayName;
        this.price = price;
        this.productUUID = productUUID;
    }
}
