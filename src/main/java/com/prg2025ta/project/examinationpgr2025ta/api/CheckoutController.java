package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.api.models.ProductModel;
import com.prg2025ta.project.examinationpgr2025ta.api.models.SessionCart;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

        Map<Product, Integer> productsInStock = ApiApplication.warehouse.getProductsInStock();
        List<ProductModel> productModelList = getProductsAsModel(productsInStock.keySet().stream().toList());
        model.addAttribute("products", productModelList);
        model.addAttribute("productsInCart", sessionCart.getProductsBought());

        return "cart";
    }
}
