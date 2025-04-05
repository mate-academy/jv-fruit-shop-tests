package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static Map<String, Integer> storage = new HashMap<>();

    public Storage(Map<String, Integer> fruits) {
        this.storage = fruits;
    }

    public static Map<String, Integer> getStorage() {
        return storage;
    }

    public static void setStorage(Map<String, Integer> storage) {
        Storage.storage = storage;
    }

    public static Map<String, Integer> getFruits() {
        return storage;
    }

    public static void addFruit(String fruit, int quantity) {
        storage.put(fruit, storage.getOrDefault(fruit, 0) + quantity);
        if (fruit == null) {
            throw new RuntimeException("Invalid fruit is null!");
        }
    }

    public static void removeFruit(String fruit, int quantity) {
        if (storage.get(fruit) == null
                || quantity > storage.get(fruit)) {
            throw new RuntimeException("No this fruit on the storage! Avaiable: "
                    + storage.getOrDefault(fruit, 0) + "   Requested: " + quantity);
        }
        storage.put(fruit, storage.get(fruit) - quantity);
    }
}
