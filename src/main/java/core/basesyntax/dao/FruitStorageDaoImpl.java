package core.basesyntax.dao;

import core.basesyntax.db.Storage;

import java.util.Map;
import java.util.Set;

public class FruitStorageDaoImpl implements FruitStorageDao {
    @Override
    public void add(String fruitName, int quantity) {
        Storage.storage.put(fruitName, quantity);
    }

    @Override
    public void update(String fruitName, int quantity) {
        Storage.storage.put(fruitName, Storage.storage.get(fruitName) - quantity);
    }

    @Override
    public Set<Map.Entry<String, Integer>> getAll() {
        return Storage.storage.entrySet();
    }
}
