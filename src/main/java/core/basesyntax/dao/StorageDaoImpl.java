package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.product.Fruit;
import java.util.Map;
import java.util.Optional;

public class StorageDaoImpl implements StorageDao {
    @Override
    public void add(Fruit fruit, int count) {
        if (fruit != null && fruit.getName() != null && count >= 0) {
            Storage.fruits.put(fruit, count);
        }
    }

    @Override
    public Optional<Integer> get(Fruit fruit) {
        if (fruit == null) {
            throw new RuntimeException("Fruit is null");
        }
        return Optional.ofNullable(Storage.fruits.get(fruit));
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.fruits;
    }
}
