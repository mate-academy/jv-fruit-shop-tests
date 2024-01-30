package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void addFruit(String fruit, int amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount can`t be negative");
        }
        Storage.storage.put(fruit, amount);
    }

    @Override
    public int get(String fruit) {
        return Storage.storage.get(fruit);
    }
}
