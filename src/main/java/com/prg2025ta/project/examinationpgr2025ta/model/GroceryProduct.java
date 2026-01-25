package com.prg2025ta.project.examinationpgr2025ta.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("GROCERY")
public class GroceryProduct extends Product {
    @Column(name = "date_of_expiry")
    private LocalDate dateOfExpiry;

    @Column(name = "needs_cooling")
    private Boolean needsCooling;

    public GroceryProduct() {
        super();
    }

    public GroceryProduct(String name, Double price, Integer stock, LocalDate dateOfExpiry, Boolean needsCooling) {
        super(name, price, stock);
        this.dateOfExpiry = dateOfExpiry;
        this.needsCooling = needsCooling;
    }

    // Getters and Setters
    public LocalDate getDateOfExpiry() {
        return dateOfExpiry;
    }

    public void setDateOfExpiry(LocalDate dateOfExpiry) {
        this.dateOfExpiry = dateOfExpiry;
    }

    public Boolean getNeedsCooling() {
        return needsCooling;
    }

    public void setNeedsCooling(Boolean needsCooling) {
        this.needsCooling = needsCooling;
    }
}
