package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Storage {
    private final Map<String, Integer> fruitDb = new HashMap<>();

    public void updateFruitQuantity(String fruit, int quantityChange) {
        int currentQuantity = fruitDb.getOrDefault(fruit, 0);
        fruitDb.put(fruit, currentQuantity + quantityChange);
    }

    public Set<Map.Entry<String, Integer>> getData() {
        return fruitDb.entrySet();
    }
}
