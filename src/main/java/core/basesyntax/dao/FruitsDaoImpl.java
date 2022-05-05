package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitsDaoImpl implements FruitsDao {
    @Override
    public void addProduct(Fruit fruit, Integer count) {
        Storage.storage.put(fruit, count);
    }

    @Override
    public Integer getValue(Fruit fruit) {
        return Storage.storage.get(fruit);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.storage;
    }
}
