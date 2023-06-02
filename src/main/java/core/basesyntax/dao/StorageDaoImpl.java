package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class StorageDaoImpl implements StorageDao {
    @Override
    public void addFruit(String fruit, int quantity) {
        if (fruit == null) {
            throw new IllegalArgumentException("Fruit name cannot be null");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
        Storage.fruitMap.put(fruit, quantity);
    }

    @Override
    public int getFruitQuantity(String fruitName) {
        return Storage.fruitMap.getOrDefault(fruitName, 0);
    }

    @Override
    public Map<String, Integer> getAllFruits() {
        return Storage.fruitMap;
    }
}
