package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.SalesClass;
import com.prg2025ta.project.examinationpgr2025ta.api.models.CheckoutModel;
import com.prg2025ta.project.examinationpgr2025ta.api.models.ProductModel;
import com.prg2025ta.project.examinationpgr2025ta.api.models.SessionCart;
import com.prg2025ta.project.examinationpgr2025ta.database.DatabaseOperations;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static com.prg2025ta.project.examinationpgr2025ta.api.ProductController.getProductsAsModel;

@Controller
public class CheckoutController {
    private static final Logger log = LogManager.getLogger(CheckoutController.class);
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
        return "ID: " + HtmlUtils.htmlEscape(productUUID);
    }

    @PostMapping("/cart/remove/{productUUID}")
    @ResponseBody
    public String removeProductFromCart(@PathVariable String productUUID) {
        sessionCart.removeProductFromCart(productUUID);
        return "ID: " + HtmlUtils.htmlEscape(productUUID);
    }

    @PostMapping("/cart/payment/method/{paymentMethod}")
    @ResponseBody
    public String setPaymentMethod(@PathVariable String paymentMethod) {
        sessionCart.setPaymentMethod(paymentMethod);
        return "Method: " + HtmlUtils.htmlEscape(paymentMethod);
    }

    @PostMapping("/cart/checkout")
    @ResponseBody
    public String checkout(@ModelAttribute CheckoutModel checkoutModel) throws SQLException {
        sessionCart.setPaymentMethod(checkoutModel.getPaymentMethod());
        sessionCart.setCustomerId(Integer.parseInt(checkoutModel.getCustomerId()));

        SalesClass sale = sessionCart.getSale();
        DatabaseOperations databaseOperations = DatabaseOperations.getInstance();
        log.info("Inserting sale");
        databaseOperations.insertSale(sale);

        return "";
    }
}
