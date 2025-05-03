package core.basesyntax.dao;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import java.util.Optional;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void put(Fruit fruit, Integer value) {
        Storage.getFruitDataBase().put(fruit, value);
    }

    @Override
    public Optional<Integer> get(Fruit fruit) {
        return Optional.ofNullable(Storage.getFruitDataBase().get(fruit));
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.getFruitDataBase();
    }
}
