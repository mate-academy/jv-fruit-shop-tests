package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;

import java.util.Map;

public class FruitStorageDaoImpl implements FruitStorageDao {

    @Override
    public void add(String fruit, int quantity) {
        if (quantity <= 0 || fruit == null || fruit.length() == 0) throw new IllegalArgumentException();
        FruitStorage.fruitToStorageQuantityMap.put(fruit, quantity);
    }

    @Override
    public int getQuantity(String fruit) {
        String lowerCaseFruit = fruit.toLowerCase();
        if (FruitStorage.fruitToStorageQuantityMap.get(lowerCaseFruit) == null) return -1;
        return FruitStorage.fruitToStorageQuantityMap.get(lowerCaseFruit);
    }

    @Override
    public Map<String,Integer> getAll() {
        return FruitStorage.fruitToStorageQuantityMap;
    }
}
