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
        if (FruitStorage.fruitToStorageQuantityMap.get(fruit) == null) return 0;
        return FruitStorage.fruitToStorageQuantityMap.get(fruit);
    }

    @Override
    public Map<String,Integer> getAll() {
        return FruitStorage.fruitToStorageQuantityMap;
    }
}
