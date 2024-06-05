package de.mcc;

import lombok.extern.java.Log;
import java.util.Scanner;
import static de.mcc.Warehouse.*;

@Log
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        log.warning("Welcome to the Warehouse! Choose a number.");
        log.warning("1. Show stored products.");
        log.warning("2. Search for products.");
        log.warning("3. Add or remove a product.");
        log.warning("4. Calculate monthly storage cost.");
        String str = in.nextLine();
        int msg = Integer.parseInt(str);
        Warehouse warehouse = new Warehouse();

        switch (msg) {
            case 1 -> showStoredProducts();
            case 2 -> searchForProduct();
            case 3 -> warehouse.addOrRemoveProduct();
            case 4 -> warehouse.calculateMonthlyStorageCost();
            default -> log.warning("Invalid option. Please choose a number.");
        }
    }
}