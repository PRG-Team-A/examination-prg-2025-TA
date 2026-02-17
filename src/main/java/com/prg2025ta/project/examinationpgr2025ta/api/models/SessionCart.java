package com.prg2025ta.project.examinationpgr2025ta.api.models;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class SessionCart {
    private SalesClass sale;
    private boolean hasBeenInitialized = false;

    public void initialize() {
        if (hasBeenInitialized) return;
        final int customerId = -5;
        final String paymentMethod = "";
        final List<Product> productsBought = new ArrayList<>();
        final double total = 0.0;

        sale = new SalesClass(customerId, paymentMethod, productsBought, total);
        hasBeenInitialized = true;
    }

    public void addProductToCart(Product product) {
        sale.getProductsBought().add(product);
    }
    public void removeProductFromCart(String productUUID) {
        for (int i = 0; i < sale.getProductsBought().size(); i++) {
            if (sale.getProductsBought().get(i).getUuid().toString().equals(productUUID)) {
                sale.getProductsBought().remove(i);
                return;
            }
        }
    }

    public double getTotal() {
        double total = 0.0;
        for (Product product : sale.getProductsBought()) {
            total += product.getPrice();
        }
        return total;
    }

    public void setCustomerId(int customerId) {
        this.sale.setCustomerID(customerId);
    }
    public void setPaymentMethod(String paymentMethod) {
        this.sale.setPaymentMethod(paymentMethod);
    }
    public String getPaymentMethod() {
        return this.sale.getPaymentMethod();
    }
    public List<Product> getProductsBought() {
        return this.sale.getProductsBought();
    }

    public SalesClass getSale() {
        return sale;
    }
}
