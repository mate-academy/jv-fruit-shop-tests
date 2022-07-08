package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    private final Map<String, Integer> storage;

    public FruitDaoImpl() {
        this.storage = Storage.fruitsMap;
    }

    public FruitDaoImpl(Map<String, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public void update(String key, int value) {
        storage.put(key, value);
    }

    @Override
    public Map<String, Integer> getAll() {
        return storage;
    }

    @Override
    public int get(String key) {
        return storage.getOrDefault(key, 0);
    }
}
