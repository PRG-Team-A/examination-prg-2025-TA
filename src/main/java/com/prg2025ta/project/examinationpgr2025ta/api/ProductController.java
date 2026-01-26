package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.api.models.ProductModel;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {
    @GetMapping("/")
    public String person(Model model) {
        // FIXME: This is only a proof-of-concept implementation and should be changed to include "real" implementation

        Map<Product, Integer> productsInStock = ApiApplication.warehouse.getProductsInStock();
        List<ProductModel> productModelList = new ArrayList<>();

        productsInStock.forEach((product, index) -> {
            productModelList.add(new ProductModel(product.getDisplayName(), product.getPrice()));
        });


        model.addAttribute("products", productModelList);
        return "product";
    }

    @PostMapping("/product")
    public RedirectView product(@ModelAttribute ProductModel productModel) {
        Product product = new GroceryProduct(productModel.getDisplayName(), productModel.getPrice());
        ApiApplication.warehouse.acceptDelivery(product);
        return new RedirectView("/");
    }
}
