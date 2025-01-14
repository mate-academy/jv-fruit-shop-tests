package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<String, Integer> inventory = new HashMap<>();

    public void add(String fruit, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to add must be "
                    + "greater than 0");
        }
        if (!inventory.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit " + fruit
                    + " is not present in the inventory");
        }
        inventory.put(fruit, inventory.get(fruit) + quantity);
    }

    public void set(String fruit, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to set must be "
                    + "greater than 0");
        }
        if (inventory.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit " + fruit
                    + " is already present in the inventory");
        }
        inventory.put(fruit, quantity);
    }

    public void remove(String fruit, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity to remove must be "
                    + "greater than 0");
        }
        if (!inventory.containsKey(fruit)) {
            throw new IllegalArgumentException("Fruit " + fruit + " not found in inventory");
        }
        int currentQuantity = inventory.get(fruit);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Insufficient quantity of " + fruit
                    + " in inventory. " + "Current quantity: " + currentQuantity);
        }

        inventory.put(fruit, currentQuantity - quantity);
    }

    public void clear() {
        inventory.clear();
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
