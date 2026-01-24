package com.prg2025ta.project.examinationpgr2025ta;
import java.util.Arrays;
import java.util.List;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

public class SalesClass {

    private int customerID;
    private String paymentMethod;
    private List<Product> productsBought;
    private double total;

    // Constructor
    public SalesClass(int customerID, String paymentMethod, List<Product> productsBought, double total) {
        this.customerID = customerID;
        this.paymentMethod = paymentMethod;
        this.productsBought = productsBought;
        this.total = total;
    }
}
