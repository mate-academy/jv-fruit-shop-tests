package core.basesyntax.dao;

import core.basesyntax.db.FruitsStorage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    private final Map<String, Integer> storage;

    public FruitDaoImpl() {
        this.storage = FruitsStorage.fruitsStorage;
    }

    public FruitDaoImpl(Map<String, Integer> storage) {
        this.storage = storage;
    }

    @Override
    public Map<String, Integer> getData() {
        if (storage.isEmpty()) {
            throw new RuntimeException("Fruit storage is empty!");
        }
        return storage;
    }

    @Override
    public Integer getFruitQuantity(String fruit) {
        return storage.entrySet().stream()
                .filter(a -> a.getKey().equalsIgnoreCase(fruit))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No such fruit in storage! " + fruit));
    }

    @Override
    public boolean update(String fruit, Integer quantity) {
        if (fruit == null || quantity == null) {
            throw new RuntimeException("You can't put to storage NULL!");
        }
        storage.put(fruit, quantity);
        return (storage.containsKey(fruit)
                && storage.get(fruit).equals(quantity));
    }
}
