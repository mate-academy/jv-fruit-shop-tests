package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitStorageDaoImpl implements FruitStorageDao {
    @Override
    public Fruit add(Fruit fruit, Integer quantity) {
        Storage.storage.put(fruit, quantity);
        return fruit;
    }

    @Override
    public int get(Fruit fruit) {
        return Storage.storage.get(fruit);
    }

    @Override
    public boolean contains(Fruit fruit) {
        return Storage.storage.containsKey(fruit);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.storage;
    }
}
