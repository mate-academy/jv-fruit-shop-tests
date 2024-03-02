package core.basesyntax.dao.impl;

import java.util.Map;

public interface FruitDao {
    Map<String, Integer> getStorage();

    int getQualityByItemType(String item);

    void putToStorage(String item, int quality);
}
