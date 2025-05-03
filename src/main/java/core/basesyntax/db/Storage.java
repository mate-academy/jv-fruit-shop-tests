package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<String, Integer> fruitsInventory = new HashMap<>();

    public Map<String, Integer> getFruitsInventory() {
        return fruitsInventory;
    }

    private int getFruitsInventory(String fruit) {
        return fruitsInventory.getOrDefault(fruit, 0);
    }

    public void setFruitsQuantity(String fruit, int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
        fruitsInventory.put(fruit, quantity);
    }

    public void increaseFruitsQuantity(String fruit, int quantity) {
        fruitsInventory.merge(fruit, quantity, Integer::sum);
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative");
        }
    }

    public void decreaseFruitsQuantity(String fruit, int quantity) {
        if (getFruitsInventory(fruit) < quantity) {
            throw new RuntimeException("Not enough fruits in inventory");
        }
        fruitsInventory.merge(fruit, -quantity, Integer::sum);
    }
}

