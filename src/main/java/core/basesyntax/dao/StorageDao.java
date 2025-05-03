package core.basesyntax.dao;

import core.basesyntax.model.FruitModel;

public interface StorageDao {
    int getAmount(String key);

    boolean putFruitModel(FruitModel fruitModel);

    boolean replaceWithNewAmount(String name, int amount);

    boolean containsKey(String key);
}
