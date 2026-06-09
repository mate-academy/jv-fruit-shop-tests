package core.basesyntax.db;

import java.util.LinkedHashMap;
import java.util.Map;

public class Storage {
    private final Map<String, Integer> storage = new LinkedHashMap<>();

    public Integer get(String fruitName) {
        return storage.get(fruitName);
    }

    public void put(String fruitName, Integer quantity) {
        storage.put(fruitName, quantity);
    }

    public Map<String, Integer> getAll() {
        return storage;
    }
}
