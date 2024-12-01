package core.basesyntax.service;

import java.util.HashMap;
import java.util.Map;

public class FruitDB {
    private static final FruitDB INSTANCE = new FruitDB();
    private final Map<String, Integer> inventory = new HashMap<>();

    private FruitDB() {
    }

    public static FruitDB getInstance() {
        return INSTANCE;
    }

    public void add(String fruit, int quantity) {
        inventory.put(fruit, inventory.getOrDefault(fruit, 0) + quantity);
    }

    public void subtract(String fruit, int quantity) {
        if (!inventory.containsKey(fruit) || inventory.get(fruit) < quantity) {
            throw new IllegalArgumentException("Not enough inventory to subtract");
        }
        inventory.put(fruit, inventory.get(fruit) - quantity);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }
}

