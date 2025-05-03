package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruits = new HashMap<>();

    public static synchronized void addFruit(String fruit, int quantity) {
        fruits.merge(fruit, quantity, Integer::sum);
    }

    public static synchronized void removeFruit(String fruit, int quantity) {
        if (!fruits.containsKey(fruit) || fruits.get(fruit) < quantity) {
            throw new IllegalArgumentException("Not enough " + fruit + " in storage");
        }
        fruits.put(fruit, fruits.get(fruit) - quantity);
    }

    public static synchronized void setFruit(String fruit, int quantity) {
        fruits.put(fruit, quantity);
    }

    public static synchronized Map<String, Integer> getAllFruits() {
        return new HashMap<>(fruits);
    }

    public static synchronized void clear() {
        fruits.clear();
    }
}
