package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void update(String key, int value) {
        Storage.fruitsMap.put(key, value);
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruitsMap;
    }

    @Override
    public int get(String key) {
        return Storage.fruitsMap.get(key) == null ? 0 : Storage.fruitsMap.get(key);
    }
}
