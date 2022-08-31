package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void addFruit(String fruit, int quantity) {
        Storage.fruits.put(fruit, quantity);
    }

    @Override
    public int getQuantity(String fruit) {
        if (Storage.fruits.containsKey(fruit)) {
            return Storage.fruits.get(fruit);
        } else {
            throw new RuntimeException("Can not find: " + fruit);
        }
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruits;
    }
}
