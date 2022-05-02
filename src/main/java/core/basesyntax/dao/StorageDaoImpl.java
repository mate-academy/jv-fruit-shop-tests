package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Set;

public class StorageDaoImpl implements StorageDao {
    @Override
    public boolean add(Fruit fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new RuntimeException("Invalid data");
        }
        Storage.storage.put(fruit, quantity);
        return true;
    }

    @Override
    public Integer get(Fruit fruit) {
        return Storage.storage.get(fruit);
    }

    @Override
    public Set<Map.Entry<Fruit, Integer>> getAll() {
        return Storage.storage.entrySet();
    }

    @Override
    public void clear() {
        Storage.storage.clear();
    }
}
