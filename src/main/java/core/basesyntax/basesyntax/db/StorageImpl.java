package core.basesyntax.basesyntax.db;

import java.util.HashMap;
import java.util.Map;

public class StorageImpl implements Storage {
    private static final int DEFAULT_VALUE = 0;

    private final Map<String, Integer> data = new HashMap<>();

    @Override
    public int getQuantity(String fruit) {
        return data.getOrDefault(fruit, DEFAULT_VALUE);
    }

    @Override
    public void setQuantity(String fruit, int quantity) {
        data.put(fruit, quantity);
    }

    @Override
    public Map<String, Integer> getAll() {
        return new HashMap<>(data);
    }

    @Override
    public void clear() {
        data.clear();
    }
}
