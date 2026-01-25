package com.prg2025ta.project.examinationpgr2025ta.dto;

import com.prg2025ta.project.examinationpgr2025ta.model.PaymentMethod;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class SaleRequest {
    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Product list is required")
    private List<ProductItem> products;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    public SaleRequest() {
    }

    public SaleRequest(Long employeeId, List<ProductItem> products, PaymentMethod paymentMethod) {
        this.employeeId = employeeId;
        this.products = products;
        this.paymentMethod = paymentMethod;
    }

    // Getters and Setters
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public List<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Inner class for product items
    public static class ProductItem {
        @NotNull(message = "Product ID is required")
        private Long productId;

        @NotNull(message = "Quantity is required")
        private Integer quantity;

        public ProductItem() {
        }

        public ProductItem(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }

        // Getters and Setters
        public Long getProductId() {
            return productId;
        }

        public void setProductId(Long productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}
