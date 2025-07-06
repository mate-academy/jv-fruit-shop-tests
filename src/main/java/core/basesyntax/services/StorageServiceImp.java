package core.basesyntax.services;

import java.util.HashMap;
import java.util.Map;

public class StorageServiceImp implements StorageService {
    private final Map<String, Integer> fruitMap = new HashMap<>();

    @Override
    public void add(String fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new IllegalArgumentException("Fruit name cannot be null and "
                    + "quantity must be non-negative");
        }
        if (quantity == 0) {
            return;
        }
        fruitMap.put(fruit, fruitMap.getOrDefault(fruit, 0) + quantity);
    }

    @Override
    public void remove(String fruit, int quantity) {
        if (fruit == null || !fruitMap.containsKey(fruit)) {
            throw new RuntimeException("Fruit not found in storage: " + fruit);
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to remove cannot be negative");
        }
        int currentQuantity = fruitMap.get(fruit);
        if (currentQuantity < quantity) {
            throw new RuntimeException("Not enough " + fruit + " in storage to remove " + quantity);
        }
        int newQuantity = currentQuantity - quantity;
        if (newQuantity == 0) {
            fruitMap.remove(fruit);
        } else {
            fruitMap.put(fruit, newQuantity);
        }
    }

    @Override
    public int getQuantity(String fruit) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit name cannot be null");
        }
        return fruitMap.getOrDefault(fruit, 0);
    }

    @Override
    public Map<String, Integer> getAll() {
        return Map.copyOf(fruitMap);
    }

    @Override
    public void clear() {
        fruitMap.clear();
    }

    @Override
    public void update(String fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new IllegalArgumentException("Fruit name cannot "
                    + "be null and quantity must be non-negative");
        }
        fruitMap.put(fruit, quantity);
    }
}
