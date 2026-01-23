package com.prg2025ta.project.examinationpgr2025ta;
import java.util.Arrays;
import java.util.List;

public class SalesClass {

    private int customerID;
    private String paymentMethod;
    private List<String> productsBought;
    private double total;

    // Constructor
    public SalesClass(int customerID, String paymentMethod, List<String> productsBought, double total) {
        this.customerID = customerID;
        this.paymentMethod = paymentMethod;
        this.productsBought = productsBought;
        this.total = total;
    }

    public static void main(String[] args) {

        // Testing the code
        SalesClass sale = new SalesClass(
                101,
                "Credit Card",
                Arrays.asList("Apple", "Banana", "Orange"),
                29.99
        );

        // Display sale details
        System.out.println("Customer ID: " + sale.customerID);
        System.out.println("Payment Method: " + sale.paymentMethod);
        System.out.println("Products Bought: " + String.join(", ", sale.productsBought));
        System.out.println("Total Amount: $" + sale.total);
    }
}
