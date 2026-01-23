package com.prg2025ta.project.examinationpgr2025ta;
import java.util.Arrays;
import java.util.List;

public class SalesClass {
//Add a constructor
//Add test products (Optional)
    private int customerID;
    private String paymentMethod;
    private List<String> productsBought;
    private double total;

    public static void main(String[] args) {

        // Display sale details (will show default null/0 for now)
        System.out.println("Customer Name: " + customerID);
        System.out.println("Payment Method: " + paymentMethod);
        System.out.println("Products Bought: " + productsBought);
        System.out.println("Total Amount: $" + total);
    }
}
