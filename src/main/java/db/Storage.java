package db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> fruits = new HashMap<>();

    public static void add(String fruit, Integer amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Fruits amount can't be negative");
        }
        fruits.put(fruit, fruits.getOrDefault(fruit, 0) + amount);
    }

    public static void remove(String fruit, Integer amount) {
        if (!fruits.containsKey(fruit) || fruits.get(fruit) < amount) {
            throw new IllegalArgumentException("Not enough " + fruit + " in storage.");
        }
        fruits.put(fruit, fruits.get(fruit) - amount);
    }

    public static int getAmount(String fruit) {
        return fruits.getOrDefault(fruit, 0);
    }

    public static void setAmount(String fruit, Integer amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Fruits amount can't be negative");
        }
        fruits.put(fruit, amount);
    }

    public static void clearStorage() {
        fruits.clear();
    }
}
