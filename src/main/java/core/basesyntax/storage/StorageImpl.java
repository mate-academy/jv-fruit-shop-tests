package core.basesyntax.storage;

import java.util.HashMap;
import java.util.Map;

public class StorageImpl implements Storage {
    private Map<String, Integer> storage;

    public StorageImpl() {
        this.storage = new HashMap<>();
    }

    @Override
    public void add(String fruit, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Incorrect data " + quantity);
        }
        storage.put(fruit,
                (storage.get(fruit) == null ? quantity : storage.get(fruit) + quantity));
    }

    @Override
    public void removeFromStorage(String fruit, Integer quantity) {
        if (quantity == null
                || quantity <= 0
                || storage.get(fruit) == null
                || quantity > storage.get(fruit)) {
            throw new RuntimeException("Incorrect data " + fruit + " " + quantity);
        }
        storage.put(fruit,
                (storage.get(fruit) == null ? quantity : storage.get(fruit) - quantity));
    }

    @Override
    public Map<String, Integer> getAllData() {
        return storage;
    }
}
