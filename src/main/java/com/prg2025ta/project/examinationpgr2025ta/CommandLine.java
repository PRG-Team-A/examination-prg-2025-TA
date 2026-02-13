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

                    for (int i = 0; i < productNames.length; i++) {
                        String name = productNames[i].trim();
