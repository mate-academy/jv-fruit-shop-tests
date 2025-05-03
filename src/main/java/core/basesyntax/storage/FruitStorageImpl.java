package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class FruitStorageImpl implements FruitStorage {
    private Map<String, Integer> fruits = new HashMap<>();

    @Override
    public void addFruits(String fruit, int quantity) {
        fruits.put(fruit, quantity);
    }

    @Override
    public void supplyFruits(String fruit, int quantity) {
        fruits.put(fruit, fruits.getOrDefault(fruit, 0) + quantity);
    }

    @Override
    public int getQuantity(String fruit) {
        return fruits.getOrDefault(fruit, 0);
    }

    @Override
    public Map<String, Integer> getFruits() {
        return fruits;
    }
}
