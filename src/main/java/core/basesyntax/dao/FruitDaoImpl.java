package core.basesyntax.dao;

import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;

public class FruitDaoImpl implements FruitDao {
    @Override
    public void put(Fruit fruit, Integer amount) {
        new StorageImpl().getStorage().put(fruit, amount);
    }

    @Override
    public Integer getAmountCurrentFruitInShop(Fruit fruit) {
        return new StorageImpl().getStorage().get(fruit);
    }

    @Override
    public void update(Fruit fruit, Integer newValue) {
        new StorageImpl().getStorage().replace(fruit, newValue);
    }

    @Override
    public Fruit remove() {
        return null;
    }
}
