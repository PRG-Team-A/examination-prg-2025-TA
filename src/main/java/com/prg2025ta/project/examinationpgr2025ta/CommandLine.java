package com.prg2025ta.project.examinationpgr2025ta;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.prg2025ta.project.examinationpgr2025ta.products.Product;

public class CommandLine {

    private static List<SalesClass> salesRecords = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose a command:");
            System.out.println("1. add-sale");
            System.out.println("2. list-sales");
            System.out.println("3. exit");
            System.out.print("Enter command: ");

            String command = scanner.nextLine();

            // ADD SALE
            if (command.equalsIgnoreCase("add-sale") || command.equals("1")) {
                try {
                    System.out.print("Enter Customer ID: ");
                    int customerID = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter Payment Method: ");
                    String paymentMethod = scanner.nextLine();

                    System.out.print("Enter Total Amount: ");
                    double total = Double.parseDouble(scanner.nextLine());

                    System.out.print("Enter products (comma separated): ");
                    String productInput = scanner.nextLine();

                    String[] productNames = productInput.split(",");
                    List<Product> productsBought = new ArrayList<>();

                    for (String name : productNames) {
                        productsBought.add(new Product(name.trim()));
                    }

                    SalesClass sale = new SalesClass(customerID, paymentMethod, total, productsBought);
                    salesRecords.add(sale);

                    System.out.println("Sale added successfully.");

                } catch (Exception e) {
                    System.out.println("Invalid input! Try again.");
                }
            }

            // LIST SALES
            else if (command.equalsIgnoreCase("list-sales") || command.equals("2")) {
                if (salesRecords.isEmpty()) {
                    System.out.println("No sales recorded yet.");
                } else {
                    for (SalesClass sale : salesRecords) {
                        System.out.println(sale);
                    }
                }
            }

            // EXIT
            else if (command.equalsIgnoreCase("exit") || command.equals("3")) {
                System.out.println("Goodbye!");
                break;
            }

            // INVALID COMMAND
            else {
                System.out.println("Invalid command. Please try again.");
            }
        }

        scanner.close();
    }
}
