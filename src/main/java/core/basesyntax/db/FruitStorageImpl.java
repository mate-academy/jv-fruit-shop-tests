package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class FruitStorageImpl implements FruitStorage {
    private static Map<String, Integer> fruitStorage = new HashMap<>();

    @Override
    public void add(String fruitName, Integer quantity) {
        if (fruitName.equals(null)) {
            throw new RuntimeException("Cannot add an empty value " + fruitName);
        }
        if (quantity.equals(null)) {
            throw new RuntimeException("Cannot add an empty value " + quantity);
        }
        fruitStorage.put(fruitName, quantity);
    }

    @Override
    public Integer getQuantity(String fruitName) {
        if (fruitName.equals(null)) {
            throw new RuntimeException("You passed an empty value for " + fruitName);
        }
        return fruitStorage.get(fruitName);
    }

    @Override
    public Map<String, Integer> getAll() {
        return fruitStorage;
    }

    @Override
    public void clear() {
        fruitStorage.clear();
    }
}
