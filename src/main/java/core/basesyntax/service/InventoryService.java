package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;

public class InventoryService {
    private Map<String, Integer> inventory = new HashMap<>();

    public void addFruit(String fruit, int quantity) {
        inventory.put(fruit, inventory.getOrDefault(fruit, 0) + quantity);
    }

    public int getQuantity(String fruit) {
        return inventory.getOrDefault(fruit, 0);
    }

    public void removeFruit(String fruit, int quantity) {
        int currentQuantity = inventory.getOrDefault(fruit, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough fruit to remove");
        }
        inventory.put(fruit, currentQuantity - quantity);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}
