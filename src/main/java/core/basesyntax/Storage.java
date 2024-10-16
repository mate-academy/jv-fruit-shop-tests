package core.basesyntax;

import java.util.HashMap;
import java.util.Map;

public class Storage {

    private Map<String, Integer> fruits = new HashMap<>();

    public void setFruitQuantity(String fruit, int quantity) {
        fruits.put(fruit, quantity);
    }

    public void addFruitQuantity(String fruit, int quantity) {
        fruits.put(fruit, fruits.getOrDefault(fruit, 0) + quantity);
    }

    public void reduceFruitQuantity(String fruit, int quantity) {
        int currentQuantity = fruits.getOrDefault(fruit, 0);
        int newQuantity = currentQuantity - quantity;

        if (newQuantity < 0) {
            throw new RuntimeException("Insufficient quantity of " + fruit + ". Current balance: "
                + currentQuantity + ", requested: " + quantity);
        }
        fruits.put(fruit, newQuantity);
    }

    public Map<String, Integer> getAllFruits() {
        return new HashMap<>(fruits);
    }
}
