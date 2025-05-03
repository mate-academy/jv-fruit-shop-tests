package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void set(Fruit fruit, int quantity) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null.");
        }
        Storage.fruits.put(fruit, quantity);
    }

    @Override
    public void add(Fruit fruit, int quantity) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null.");
        }
        Storage.fruits.put(fruit, Storage.fruits.get(fruit) + quantity);
    }

    @Override
    public void subtract(Fruit fruit, int quantity) {
        if (fruit == null || quantity > Storage.fruits.get(fruit)) {
            throw new RuntimeException("There are no fruits for purchase.");
        }
        Storage.fruits.put(fruit, Storage.fruits.get(fruit) - quantity);
    }

    @Override
    public int get(Fruit fruit) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null.");
        }
        return Storage.fruits.get(fruit);
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return new HashMap<>(Storage.fruits);
    }
}
