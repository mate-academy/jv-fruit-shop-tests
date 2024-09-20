package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<String, Integer> fruits = new HashMap<>();

    public void addFruit(String fruit, int quantity) {
        fruits.merge(fruit, quantity, Integer::sum);
    }

    public void removeFruit(String fruit, int quantity) {
        fruits.put(fruit, fruits.getOrDefault(fruit, 0) - quantity);
        if (fruits.get(fruit) < 0) {
            throw new RuntimeException("Number of fruits can't be negative");
        }
    }

    public Map<String, Integer> getAllFruits() {
        return fruits;
    }
}
