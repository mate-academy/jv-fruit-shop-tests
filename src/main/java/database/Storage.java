package database;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> assortment = new LinkedHashMap<>();

    public static Map<String, Integer> getAssortment() {
        return Map.copyOf(assortment);
    }

    static void updateStorage(String fruit, int quantity) {
        int finalQuantity = assortment.containsKey(fruit)
                ? assortment.get(fruit) + quantity : quantity;
        assortment.put(fruit, finalQuantity);
    }

    public static void clearStorage() {
        assortment.clear();
    }

    public static void addToStorage(String fruit, int quantity) {
        assortment.put(fruit, quantity);
    }
}
