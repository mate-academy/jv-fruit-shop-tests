package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import java.util.Map;
import java.util.Set;

public class FruitDaoImpl implements FruitDao {
    @Override
    public Set<Map.Entry<String, Integer>> getMapEntry() {
        return FruitStorage.fruits.entrySet();
    }

    @Override
    public Integer getQuantity(String fruit) {
        return FruitStorage.fruits.get(fruit);
    }

    @Override
    public void updateQuantity(String fruit, Integer quantity) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        FruitStorage.fruits.put(fruit, quantity);
    }

    @Override
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Override
    public boolean containsFruit(String key) {
        return FruitStorage.fruits.containsKey(key);
    }
}
