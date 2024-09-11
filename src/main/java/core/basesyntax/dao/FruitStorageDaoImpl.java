package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import java.util.Map;

public class FruitStorageDaoImpl implements FruitStorageDao {

    @Override
    public Map<String, Integer> getAllFruits() {
        return FruitStorage.fruitStorage;
    }

    @Override
    public int getFruitQuantity(String fruit) {
        return FruitStorage.fruitStorage.get(fruit);
    }

    @Override
    public int add(String fruit, int quantity) {
        if (!FruitStorage.fruitStorage.containsKey(fruit)) {
            getAllFruits().put(fruit, quantity);
            return quantity;
        }
        int oldValue = getAllFruits().get(fruit);
        getAllFruits().put(fruit, quantity);
        return oldValue;
    }

    @Override
    public int calculateTotalQuantity() {
        return getAllFruits().values().stream().mapToInt(Integer::intValue).sum();
    }
}
