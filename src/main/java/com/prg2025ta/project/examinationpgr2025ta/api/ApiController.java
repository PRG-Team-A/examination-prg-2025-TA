package com.prg2025ta.project.examinationpgr2025ta.api;

import com.prg2025ta.project.examinationpgr2025ta.Warehouse;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@RestController
@SpringBootApplication
public class ApiController {

    private static Warehouse warehouse = new Warehouse();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setWarehouse(Warehouse warehouse) {
        ApiController.warehouse = warehouse;
    }

    public static void main(String[] args) {
        SpringApplication.run(ApiController.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "<h1>Index</h1>";
    }

    @GetMapping("/list/products")
    public String listProducts() {
        Map<Product, Integer> productsInStock = warehouse.getProductsInStock();
        return objectMapper.writeValueAsString(productsInStock);
    }

    @PutMapping("/product")
    public String putProduct(@Validated @RequestBody Product product, Errors errors) {
        errors.getAllErrors().iterator().forEachRemaining(objectError -> {
            System.out.println(objectError.toString());
        });
        warehouse.acceptDelivery(product);
        return "{'status': 'success'}";
    }
}
