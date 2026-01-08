package com.prg2025ta.project.examinationpgr2025ta;

import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.time.LocalDate;
import java.util.*;

public class Warehouse {
    private Map<Product, Integer> productsInStock = new HashMap<>();

    public Warehouse() {}
    public Warehouse(Product... products) { acceptDelivery(products); }

    /**
     * Adds each product to the warehouse, or if the product
     * already exists in the warehouse, increments the amount
     * associated with that product
     */
    public void acceptDelivery(Product... products) {
        for (Product product : products) {
            if (productsInStock.containsKey(product)) {
                this.productsInStock.put(product, this.productsInStock.get(product) + 1);
            }
        }
    }

    /**
     * @return How often this product exists in this warehouse
     */
    public int getAmountOfProductInStock(Product product) {
        Integer amount = this.productsInStock.get(product);
        if (Objects.isNull(amount)) return 0;
        return amount;
    }

    public void purgeExpiredProducts() {
        List<Product> expiredProducts = new ArrayList<>();
        LocalDate today = LocalDate.now();

        for (Product product : this.productsInStock.keySet()) {
            if (!(product instanceof GroceryProduct groceryProduct)) { continue; }

            if (groceryProduct.getDateOfExpiry().isBefore(today)) {
                expiredProducts.add(groceryProduct);
            }
        }
        for (Product expiredProduct : expiredProducts) {
            this.productsInStock.remove(expiredProduct);
        }
    }

    public void removeFromWarehouse(Product product) {

    }
}
