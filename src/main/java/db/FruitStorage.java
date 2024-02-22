package db;

import java.util.HashMap;
import java.util.Map;

public class FruitStorage {
    private static Map<String, Integer> fruits;

    public FruitStorage() {
        fruits = new HashMap<>();
    }

    public Map<String, Integer> getFruits() {
        return fruits;
    }
}
