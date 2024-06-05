package de.mcc;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;
import java.util.ArrayList;
import java.util.Scanner;

@Log
@AllArgsConstructor
@Getter
@Setter
public class Warehouse {
    public static ArrayList<Product> storedProducts = new ArrayList<>();

    static {
        storedProducts.add(new Product("Apple", 1, "fruit", 10, 0.2,  0.5, false));
        storedProducts.add(new Product("Pear", 2, "fruit", 10, 0.2, 0.5, false));
        storedProducts.add(new Product("Tomato", 1, "vegetable", 10, 0.4, 0.7, false));
        storedProducts.add(new Product("Cucumber", 1, "vegetable", 10, 0.4, 0.6, false));
        storedProducts.add(new Product("Chicken", 5, "meat", 10, 1, 2, true));
        storedProducts.add(new Product("Pork", 6, "meat", 10, 1, 2, true));
        storedProducts.add(new Product("Laptop", 30, "technology", 10, 2.5, 1, false));
        storedProducts.add(new Product("TV", 50, "technology", 10, 10, 1, false));
        storedProducts.add(new Product("Soil", 1, "bulk", 100, 100, 0.3, false));
        storedProducts.add(new Product("Concrete", 2, "bulk", 200, 200, 0.4, false));
    }

    static void showStoredProducts() {
        for(Product p: storedProducts){
            log.warning("- " + p);
        }
    }

    static void searchForProduct() {
        log.warning("Search for a name or category of product:");
        Scanner scanner = new Scanner(System.in);
        String search = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Product p : storedProducts) {
            if (p.getName().toLowerCase().contains(search) || p.getCategory().toLowerCase().contains(search)) {
                log.warning("Product found: {}"+ p);
                found = true;
            }
        }
        if (!found) {
            log.warning("Nothing found");
        }
    }

    public void addOrRemoveProduct() {
        log.warning("Enter the name of the product:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine().toLowerCase();

        log.warning("Enter the amount to add (positive) or remove (negative):");
        int amount = scanner.nextInt();
        scanner.nextLine();

        boolean productFound = false;
        for (Product p : storedProducts) {
            if (p.getName().equalsIgnoreCase(name)) {
                if (p.getCategory().equals("bulk")) {
                    p.setWeight(p.getWeight() + amount);
                } else {
                    p.setAmount(p.getAmount() + amount);
                }
                log.warning("Updated product: " + p);
                productFound = true;
                break;
            }
        }

        if (!productFound) {
            log.warning("Product not found. Would you like to add a new product? (yes/no)");
            String response = scanner.nextLine();
            if (response.equalsIgnoreCase("yes")) {
                log.warning("Enter the category of the new product:");
                String category = scanner.nextLine();
                log.warning("Enter the price of the new product:");
                int price = scanner.nextInt();
                scanner.nextLine();
                log.warning("Enter the storage cost per unit:");
                double storageCostPerUnit = scanner.nextDouble();
                scanner.nextLine();
                log.warning("Does the product require special storage? (true/false):");
                boolean specialStorageRequired = scanner.nextBoolean();
                scanner.nextLine();
                log.warning("Is the product sold by weight? (yes/no):");
                String isBulk = scanner.nextLine();
                if (isBulk.equalsIgnoreCase("yes")) {
                    log.warning("Enter the weight of the product (in kg):");
                    double weight = scanner.nextDouble();
                    scanner.nextLine();
                    storedProducts.add(new Product(name, price, category, 0, weight, storageCostPerUnit, specialStorageRequired));
                } else {
                    log.warning("Enter the quantity of the product:");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    storedProducts.add(new Product(name, price, category, quantity, 0, storageCostPerUnit, specialStorageRequired));
                }
                log.warning("New product added: " + name);
            } else {
                log.warning("Operation canceled.");
            }
        }
    }

    public void calculateMonthlyStorageCost() {
        double totalCost = 0.0;
        for (Product p : storedProducts) {
            double productCost;
            if (p.getCategory().equals("bulk")) {
                productCost = p.getWeight() * p.getStorageCostPerUnit();
            } else {
                productCost = p.getAmount() * p.getStorageCostPerUnit();
            }
            if (p.isSpecialStorageRequired()) {
                productCost *= 1.5; // I just assume special storage is 50% more expensive
            }
            totalCost += productCost;
        }
        log.warning("Total monthly storage cost: " + totalCost);
    }
}
