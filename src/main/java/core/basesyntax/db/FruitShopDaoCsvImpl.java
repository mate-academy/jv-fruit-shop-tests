package core.basesyntax.db;

import core.basesyntax.storage.Storage;
import java.util.Collections;
import java.util.Map;

public class FruitShopDaoCsvImpl implements FruitShopDao {
    @Override
    public void put(String fruit, Integer quantity) {
        Storage.fruitQuantities.put(fruit, quantity);
    }

    @Override
    public Map<String, Integer> getAllFruitsAndQuantities() {
        return Collections.unmodifiableMap(Storage.fruitQuantities);
    }

    @Override
    public Integer getFruitQuantity(String fruit) {
        return Storage.fruitQuantities.getOrDefault(fruit, 0);
    }
}
