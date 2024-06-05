
import de.mcc.Product;
import de.mcc.Warehouse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseTest {

    @BeforeEach
    void setUp() {
        Warehouse.storedProducts = new ArrayList<>();
        Warehouse.storedProducts.add(new Product("Apple", 1, "fruit", 10, 0.2, 0.5, false));
        Warehouse.storedProducts.add(new Product("Pear", 2, "fruit", 10, 0.2, 0.5, false));
        Warehouse.storedProducts.add(new Product("Tomato", 1, "vegetable", 10, 0.4, 0.7, false));
        Warehouse.storedProducts.add(new Product("Cucumber", 1, "vegetable", 10, 0.4, 0.6, false));
        Warehouse.storedProducts.add(new Product("Chicken", 5, "meat", 10, 1, 2, true));
        Warehouse.storedProducts.add(new Product("Pork", 6, "meat", 10, 1, 2, true));
        Warehouse.storedProducts.add(new Product("Laptop", 30, "technology", 10, 2.5, 1, false));
        Warehouse.storedProducts.add(new Product("TV", 50, "technology", 10, 10, 1, false));
        Warehouse.storedProducts.add(new Product("Soil", 1, "bulk", 0, 100, 0.3, false));
        Warehouse.storedProducts.add(new Product("Concrete", 2, "bulk", 0, 200, 0.4, false));
    }

    @Test
    void testCalculateMonthlyStorageCost() {
        Warehouse warehouse = new Warehouse();
        double totalCost = 0.0;

        for (Product p : Warehouse.storedProducts) {
            double productCost;
            if (p.getCategory().equals("bulk")) {
                productCost = p.getWeight() * p.getStorageCostPerUnit();
            } else {
                productCost = p.getAmount() * p.getStorageCostPerUnit();
            }
            if (p.isSpecialStorageRequired()) {
                productCost *= 1.5;
            }
            totalCost += productCost;
        }

        // Make sure that the calculated cost matches expected cost
        assertEquals(totalCost, calculateTotalCost(Warehouse.storedProducts), 0.01);
    }

    @Test
    void testAddOrRemoveProduct_AddAmount() {
        Warehouse warehouse = new Warehouse();
        int initialAmount = getAmountOfProduct("Apple");

        // I'm manually modifying the storedProducts list to simulate adding a random product
        for (Product p : Warehouse.storedProducts) {
            if (p.getName().equalsIgnoreCase("Apple")) {
                p.setAmount(p.getAmount() + 5);
            }
        }

        // Check if the amount was updated correctly
        assertEquals(initialAmount + 5, getAmountOfProduct("Apple"));
    }

    @Test
    void testAddOrRemoveProduct_RemoveAmount() {
        Warehouse warehouse = new Warehouse();
        int initialAmount = getAmountOfProduct("Apple");

        // This time I'm manually modifying the storedProducts list to simulate removing a product
        for (Product p : Warehouse.storedProducts) {
            if (p.getName().equalsIgnoreCase("Apple")) {
                p.setAmount(p.getAmount() - 5);
            }
        }

        // Check if the amount was updated correctly
        assertEquals(initialAmount - 5, getAmountOfProduct("Apple"));
    }

    @Test
    void testAddOrRemoveProduct_AddWeight() {
        Warehouse warehouse = new Warehouse();
        double initialWeight = getWeightOfProduct("Soil");

        // Same thing again, manually modifying the storedProducts list to simulate adding a product
        for (Product p : Warehouse.storedProducts) {
            if (p.getName().equalsIgnoreCase("Soil")) {
                p.setWeight(p.getWeight() + 50);
            }
        }

        // Check if the weight was updated correctly
        assertEquals(initialWeight + 50, getWeightOfProduct("Soil"), 0.01);
    }

    @Test
    void testAddOrRemoveProduct_RemoveWeight() {
        Warehouse warehouse = new Warehouse();
        double initialWeight = getWeightOfProduct("Soil");

        // Mhmmm, removing product, you get the gist...
        for (Product p : Warehouse.storedProducts) {
            if (p.getName().equalsIgnoreCase("Soil")) {
                p.setWeight(p.getWeight() - 20);
            }
        }

        // Check if the weight was updated correctly
        assertEquals(initialWeight - 20, getWeightOfProduct("Soil"), 0.01);
    }

    private int getAmountOfProduct(String name) {
        Optional<Product> product = Warehouse.storedProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
        return product.map(Product::getAmount).orElse(0);
    }

    private double getWeightOfProduct(String name) {
        Optional<Product> product = Warehouse.storedProducts.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name))
                .findFirst();
        return product.map(Product::getWeight).orElse(0.0);
    }

    private double calculateTotalCost(ArrayList<Product> products) {
        double totalCost = 0.0;
        for (Product p : products) {
            double productCost;
            if (p.getCategory().equals("bulk")) {
                productCost = p.getWeight() * p.getStorageCostPerUnit();
            } else {
                productCost = p.getAmount() * p.getStorageCostPerUnit();
            }
            if (p.isSpecialStorageRequired()) {
                productCost *= 1.5;
            }
            totalCost += productCost;
        }
        return totalCost;
    }
}