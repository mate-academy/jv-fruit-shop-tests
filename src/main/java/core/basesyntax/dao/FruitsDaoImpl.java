package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitsDaoImpl implements FruitsDao {
    @Override
    public void save(Fruit fruit, int value) {
        Storage.storageFruits.put(fruit, value);
    }

    @Override
    public int get(Fruit fruit) {
        return Storage.storageFruits.getOrDefault(fruit, 0);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.storageFruits;
    }
}
