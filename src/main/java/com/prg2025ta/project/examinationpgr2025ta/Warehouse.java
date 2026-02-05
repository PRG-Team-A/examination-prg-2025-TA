package com.prg2025ta.project.examinationpgr2025ta;

import com.prg2025ta.project.examinationpgr2025ta.database.DatabaseOperations;
import com.prg2025ta.project.examinationpgr2025ta.exceptions.OutOfStockException;
import com.prg2025ta.project.examinationpgr2025ta.products.GroceryProduct;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class Warehouse {
    private Map<Product, Integer> productsInStock = new HashMap<>();

    public Map<Product, Integer> getProductsInStock() {
        return productsInStock;
    }

    public Warehouse() {}
    public Warehouse(Product... products) { acceptDelivery(products); }

    /**
     * Adds each product to the warehouse, or if the product
     * already exists in the warehouse, increments the amount
     * associated with that product
     */
    public void acceptDelivery(Product... products) {
        acceptDelivery(Arrays.stream(products).toList());
    }

    public void acceptDelivery(List<Product> products) {
        for (Product product : products) {
            if (productsInStock.containsKey(product)) {
                this.productsInStock.put(product, this.productsInStock.get(product) + 1);
            } else {
                this.productsInStock.put(product, 1);
            }
        }
    }


    public void loadFromDatabase(DatabaseOperations databaseOperations) throws SQLException {
        List<Product> products = databaseOperations.getAllProducts();
        acceptDelivery(products);
    }

    /**
     * @return How often this product exists in this warehouse
     */
    public int getAmountOfProductInStock(Product product) {
        Integer amount = this.productsInStock.get(product);
        if (Objects.isNull(amount)) return 0;
        return amount;
    }

    /**
     * Removes all products in the warehouse which are expired by today
     */
    public void purgeExpiredProducts() {
        purgeExpiredProducts(LocalDate.now());
    }

    /**
     * Removes all products in the warehouse which are expired by {@code date}
     */
    public void purgeExpiredProducts(LocalDate date) {
        List<Product> expiredProducts = new ArrayList<>();

        for (Product product : this.productsInStock.keySet()) {
            if (!(product instanceof GroceryProduct groceryProduct)) { continue; }

            if (groceryProduct.getDateOfExpiry().isBefore(date)) {
                expiredProducts.add(groceryProduct);
            }
        }
        for (Product expiredProduct : expiredProducts) {
            this.productsInStock.remove(expiredProduct);
        }
    }

    /**
     * @param product The product of which to remove one item from the warehouse
     * @throws OutOfStockException The product is not in stock
     */
    public void removeFromWarehouse(Product product) throws OutOfStockException {
        removeFromWarehouse(product, 1);
    }

    /**
     * @param product The product to remove
     * @param n How many units of this product should be removed
     * @throws OutOfStockException Not enough (or none) of this product is in the warehouse
     */
    public void removeFromWarehouse(Product product, int n) throws OutOfStockException {
        if (!this.productsInStock.containsKey(product)) throw new OutOfStockException();

        Integer stockAvailable = productsInStock.get(product);
        if (stockAvailable <= n) {
            this.productsInStock.remove(product);
        } else {
            this.productsInStock.put(product, stockAvailable - n);
        }
    }

    public Product getProductWithUuid(String uuid) {
        for (Map.Entry<Product, Integer> entry : productsInStock.entrySet()) {
            Product product = entry.getKey();
            Integer amount = entry.getValue();

            if (product.getUuid().toString().equals(uuid)) {
                return product;
            }
        }
    }
}
