package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoFruitsException;
import java.util.Map;

public class FruitsDaoImpl implements FruitsDao {
    @Override
    public void add(String fruit, int quantity) {
        Storage.fruits.put(fruit, quantity);
    }

    @Override
    public int getFruitQuantity(String fruit) {
        if (Storage.fruits.get(fruit) == null) {
            throw new NoFruitsException("This shop don't sell " + fruit + ".");
        }
        return Storage.fruits.get(fruit);
    }

    @Override
    public Map<String, Integer> getAll() {
        return Storage.fruits;
    }
}
