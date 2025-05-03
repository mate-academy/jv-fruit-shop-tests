package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import java.util.Map;

public class FruitDao implements StorageDao {

    @Override
    public Map<String, Integer> getStorage() {
        return FruitStorage.fruitStorage;
    }

    @Override
    public int getQuantityByObjectType(String fruit) {
        return FruitStorage.fruitStorage.getOrDefault(fruit, 0);
    }

    @Override
    public void putToStorage(String fruit, int quantity) {
        FruitStorage.fruitStorage.put(fruit, quantity);
    }
}
