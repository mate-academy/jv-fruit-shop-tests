package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public Map<String, Integer> getAll() {
        return Storage.FRUIT_MAP;
    }

    @Override
    public int getFruitQuantity(String fruitName) {
        return Storage.FRUIT_MAP.get(fruitName);
    }

    @Override
    public void setFruitQuantity(String fruitName, int quantity) {
        Storage.FRUIT_MAP.put(fruitName, quantity);
    }
}
