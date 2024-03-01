package core.basesyntax.dao.impl;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitDaoImpl implements FruitDao {
    @Override
    public Map<String, Integer> getStorage() {
        return Storage.fruitStorage;
    }

    @Override
    public int getQualityByItemType(String fruit) {
        Integer quality = Storage.fruitStorage.get(fruit);
        if (quality == null) {
            return 0;
        }
        return quality.intValue();
    }

    @Override
    public void putToStorage(String fruit, int quality) {
        if (quality < 0) {
            throw new IllegalArgumentException("Fruit quality cannot be negative");
        }
        Storage.fruitStorage.put(fruit, quality);
    }

}
