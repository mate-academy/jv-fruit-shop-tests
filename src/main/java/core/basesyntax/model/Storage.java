package core.basesyntax.model;

import java.util.HashMap;
import java.util.Map;

public class Storage {
    private static final Map<String, Integer> fruitStorage = new HashMap<>();

    public void put(String fruit, Integer quantity) {
        fruitStorage.put(fruit, quantity);
    }

    public Integer getQuantity(String fruit) {
        return fruitStorage.getOrDefault(fruit, 0);
    }

    public Map<String, Integer> getStorage() {
        return new HashMap<>(fruitStorage);
    }

    public void clearStorage() {
        fruitStorage.clear();
    }
}
