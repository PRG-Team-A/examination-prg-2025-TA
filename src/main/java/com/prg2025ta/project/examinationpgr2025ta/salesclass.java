import java.util.ArrayList;
import java.util.Scanner;

public class salesclass {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        double totalSum = 0;

        ArrayList<String> products = new ArrayList<>();
        ArrayList<Double> prices = new ArrayList<>();

        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        do {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add product");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            if (choice == 0) {
                break;
            }

            if (choice == 1) {
                System.out.print("Enter product name: ");
                String productName = scanner.nextLine();

                System.out.print("Enter product cost (you may use $ or €): ");
                String costInput = scanner.nextLine();

                costInput = costInput.replace("$", "").replace("€", "");

                double productCost = Double.parseDouble(costInput);

                products.add(productName);
                prices.add(productCost);
                totalSum += productCost;

                System.out.println("Product added: " + productName + " (" + productCost + ")");
            } else {
                System.out.println("Invalid option.");
            }

        } while (true);

        System.out.print("\nEnter payment method (cash/card): ");
        String paymentMethod = scanner.nextLine();

        System.out.println("\n--- Sales Receipt ---");
        System.out.println("Customer: " + customerName);
        System.out.println("\nProducts bought:");

        for (int i = 0; i < products.size(); i++) {
            System.out.println("- " + products.get(i) + ": " +"$"+ prices.get(i));
        }

        System.out.println("\nTotal Sum: " +"$" + totalSum);
        System.out.println("Payment Method: " + paymentMethod);

        scanner.close();
    }
}
