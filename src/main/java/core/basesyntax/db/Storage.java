package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> storage = new HashMap<>();

    public static void addFruit(String fruit, int quantity) {
        checkValues(fruit, quantity);
        storage.put(fruit, storage.getOrDefault(fruit, 0) + quantity);
    }

    public static void removeFruit(String fruit, int quantity) {
        checkValues(fruit, quantity);
        if (storage.containsKey(fruit) && storage.get(fruit) >= quantity) {
            storage.put(fruit, storage.get(fruit) - quantity);
        } else {
            throw new RuntimeException("Not enough fruits in storage");
        }
    }

    public static Map<String, Integer> getStorage() {
        return storage;
    }

    private static void checkValues(String fruit, int quantity) {
        if (fruit == null || fruit.isEmpty()) {
            throw new IllegalArgumentException("Fruit value can't be empty");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity can't be less than 1");
        }
    }
}
