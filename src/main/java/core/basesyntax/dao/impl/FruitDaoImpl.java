package core.basesyntax.dao.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void update(Fruit fruit, Integer quantity) {
        if (fruit == null) {
            throw new RuntimeException("Fruit shouldn't be null");
        } else {
            Storage.fruits.put(fruit, Math.abs(quantity));
        }
    }

    @Override
    public Integer getQuantity(Fruit fruit) {
        Integer quantity = Storage.fruits.entrySet().stream()
                .filter(k -> k.getKey().equals(fruit))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wrong key. "
                        + "Can't find element in storage"));
        if (quantity == null) {
            throw new RuntimeException("Quantity is null");
        }
        return quantity;
    }

    @Override
    public Map<Fruit, Integer> getAll() {
        return Storage.fruits;
    }
}
