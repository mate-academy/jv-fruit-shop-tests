package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class FruitStorage {
    private static Map<String, Integer> fruits;

    public FruitStorage() {
        fruits = new HashMap<>();
    }

    public void addFruit(String fruitName, int quantity) {
        fruits.put(fruitName, fruits.getOrDefault(fruitName, 0) + quantity);
    }

    public void removeFruit(String fruitName, int quantity) {
        if (!fruits.containsKey(fruitName)) {
            throw new IllegalArgumentException("Fruit not found: " + fruitName);
        }
        int currentQuantity = fruits.get(fruitName);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough " + fruitName + " in stock");
        }

        int updatedQuantity = currentQuantity - quantity;
        fruits.put(fruitName, updatedQuantity);
    }

    public Map<String, Integer> getFruits() {
        return fruits;
    }

    public int getFruitQuantity(String fruit) {
        return fruits.getOrDefault(fruit, 0);
    }
}
