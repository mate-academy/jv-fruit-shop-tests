package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class FruitShopDaoImpl implements FruitShopDao {
    @Override
    public void add(String fruit, int amount) {
        Storage.storage.put(fruit, amount);
    }
}
