package com.prg2025ta.project.examinationpgr2025ta;
import java.util.ArrayList;
import java.util.List;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;
public class CliMain {

    private static List<SalesClass> salesRecords = new ArrayList<>();
    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }
        
        String command = args[0];
        if (command.equals("add-sale")) {
            if (args.length < 5) {
                System.out.println("Usage: add-sale <customerID> <paymentMethod> <total> <product1,product2,...>");
                return;
            }
            int customerID = 0;
            double total = 0.0;
            try {
                customerID = Integer.parseInt(args[1]);
                total = Double.parseDouble(args[3]);
            } catch (NumberFormatException e) {
                System.out.println("Customer ID and Total must be numbers.");
                return;
            }

            String paymentMethod = args[2];
            String[] productNames = args[4].split(",");

            List<Product> productsBought = new ArrayList<>();
            for (int i = 0; i < productNames.length; i++) {
                productsBought.add(new Product(productNames[i]));
            }

            SalesClass sale = new SalesClass(customerID, paymentMethod, productsBought, total);
            salesRecords.add(sale);
            System.out.println("Sale added successfully.");

        } else if (command.equals("list-sales")) {
            if (salesRecords.size() == 0) {
                System.out.println("No sales records found.");
            } else {
                for (int i = 0; i < salesRecords.size(); i++) {
                    SalesClass sale = salesRecords.get(i);
                    System.out.print("CustomerID: " + sale.getCustomerID());
                    System.out.print(", Payment: " + sale.getPaymentMethod());
                    System.out.print(", Total: " + sale.getTotal());
                    System.out.print(", Products: ");
                    for (int j = 0; j < sale.getProductsBought().size(); j++) {
                        System.out.print(sale.getProductsBought().get(j).getName());
                        if (j < sale.getProductsBought().size() - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                }
            }
        } else {
            printHelp();
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  add-sale <customerID> <paymentMethod> <total> <product1,product2,...>");
        System.out.println("  list-sales");
    }
}
