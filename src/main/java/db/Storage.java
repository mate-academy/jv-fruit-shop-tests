package db;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    public static final Map<String, Integer> storage = new HashMap<>();

    public void put(String fruitName, int quantity) {
        storage.put(fruitName, quantity);
    }

    public int get(String fruitName) {
        return storage.getOrDefault(fruitName, 0);
    }
}
