package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.api.models.CheckoutModel;
import com.prg2025ta.project.examinationpgr2025ta.api.models.ProductModel;
import com.prg2025ta.project.examinationpgr2025ta.api.models.SessionCart;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.prg2025ta.project.examinationpgr2025ta.api.ProductController.getProductsAsModel;

@Controller
public class CheckoutController {
    private final SessionCart sessionCart;

    @Autowired
    public CheckoutController(SessionCart sessionCart) {
        this.sessionCart = sessionCart;
    }

    @GetMapping("/cart")
    public String cart(Model model) {
        sessionCart.initialize();

        List<ProductModel> models = getProductsAsModel(sessionCart.getProductsBought());

        Map<Product, Integer> productsInStock = ApiApplication.warehouse.getProductsInStock();
        List<ProductModel> productModelList = getProductsAsModel(productsInStock.keySet().stream().toList());
        model.addAttribute("products", productModelList);
        model.addAttribute("productsInCart", models);
        model.addAttribute("total", sessionCart.getTotal());

        return "cart";
    }

    @PostMapping("/cart/add/{productUUID}")
    @ResponseBody
    public String addProductToCart(@PathVariable String productUUID) {
        sessionCart.addProductToCart(ApiApplication.warehouse.getProductWithUuid(productUUID));
        return "ID: " + productUUID;
    }

    @PostMapping("/cart/remove/{productUUID}")
    @ResponseBody
    public String removeProductFromCart(@PathVariable String productUUID) {
        sessionCart.removeProductFromCart(productUUID);
        return "ID: " + productUUID;
    }

    @PostMapping("/cart/payment/method/{paymentMethod}")
    @ResponseBody
    public String setPaymentMethod(@PathVariable String paymentMethod) {
        sessionCart.setPaymentMethod(paymentMethod);
        return "Method: " + paymentMethod;
    }

    @PostMapping("/cart/checkout")
    @ResponseBody
    public String checkout(@ModelAttribute CheckoutModel checkoutModel) {
        sessionCart.setPaymentMethod(checkoutModel.getPaymentMethod());
        sessionCart.setCustomerId(Integer.parseInt(checkoutModel.getCustomerId()));

        return "";
    }
}
