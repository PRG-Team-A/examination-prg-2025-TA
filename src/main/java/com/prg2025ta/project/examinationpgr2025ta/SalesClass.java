package com.prg2025ta.project.examinationpgr2025ta;
import java.util.Arrays;
import java.util.List;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

public class SalesClass {

    private int saleId;
    private int customerID;
    private String paymentMethod;
    private List<Product> productsBought;
    private double total;

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<Product> getProductsBought() {
        return productsBought;
    }

    public void setProductsBought(List<Product> productsBought) {
        this.productsBought = productsBought;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getSaleId() { return saleId; }

    // Constructor
    public SalesClass(int customerID, String paymentMethod, List<Product> productsBought, double total) {
        this(-1, customerID, paymentMethod, productsBought, total);
    }
    public SalesClass(int saleId, int customerID, String paymentMethod, List<Product> productsBought, double total) {
        this.customerID = customerID;
        this.paymentMethod = paymentMethod;
        this.productsBought = productsBought;
        this.total = total;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(saleId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj.getClass() != this.getClass()) return false;

        final SalesClass other = (SalesClass) obj;
        if (other.getSaleId() != this.getSaleId()) return false;

        return true;
    }
    public String toString() {
        return "Sale{" +
               "customerID=" + customerID +
               ", paymentMethod='" + paymentMethod + '\'' +
               ", total=" + total +
               ", products=" + products +
               '}';
}
