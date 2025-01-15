package core.basesyntax.store;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruits = new HashMap<>();

    public static void modifyFruitStorage(String fruit, int quantity) {
        int newQuantity = fruits.getOrDefault(fruit, 0) + quantity;
        if (newQuantity < 0) {
            throw new IllegalStateException("Stock for fruit " + fruit + " cannot be negative.");
        }
        fruits.put(fruit, newQuantity);
    }

    public static int getFruitQuantity(String fruit) {
        return fruits.getOrDefault(fruit, 0);
    }

    public static Map<String, Integer> getAllFruits() {
        return new HashMap<>(fruits);
    }

    public static void clearStorage() {
        fruits.clear();
    }
}
