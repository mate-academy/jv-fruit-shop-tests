package core.basesyntax.db;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StorageImpl implements Storage {
    private static final Map<String, Integer> data = new HashMap<>();

    @Override
    public Integer getValue(String key) {
        return data.get(key);
    }

    @Override
    public void setValue(String key, Integer value) {
        data.put(key, value);
    }

    @Override
    public Set<String> getKeys() {
        return data.keySet();
    }

    @Override
    public void clear() {
        data.clear();
    }
}
