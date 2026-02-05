package com.prg2025ta.project.examinationpgr2025ta.api.models;

public class CheckoutModel {
    private String paymentMethod;
    private String customerId;

    public CheckoutModel(String paymentMethod, String customerId) {
        this.paymentMethod = paymentMethod;
        this.customerId = customerId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
