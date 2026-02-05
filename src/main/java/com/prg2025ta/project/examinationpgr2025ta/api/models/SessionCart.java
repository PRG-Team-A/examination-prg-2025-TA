package com.prg2025ta.project.examinationpgr2025ta.api.models;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Component
@SessionScope
public class SessionCart {
    private SalesClass sale;

    public void initialize() {
        final int customerId = -5;
        final String paymentMethod = "";
        final List<Product> productsBought = new ArrayList<>();
        final double total = 0.0;

        sale = new SalesClass(customerId, paymentMethod, productsBought, total);
    }

    public void addProductToCart(Product product) {
        sale.getProductsBought().add(product);
    }

    public double getTotal() {
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        sale.getProductsBought().forEach(product -> {
            total.updateAndGet(v -> (double) (v + product.getPrice()));
        });
        return total.get();
    }
}
