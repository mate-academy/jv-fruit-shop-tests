package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.db.impl.StorageImpl;
import core.basesyntax.model.Fruit;

public class FruitDaoImpl implements FruitDao {
    private Storage storage = new StorageImpl();

    @Override
    public void put(Fruit fruit, Integer amount) {
        storage.getStorage().put(fruit, amount);
    }

    @Override
    public Integer getAmountCurrentFruitInShop(Fruit fruit) {
        storageHasThisFruit(fruit);
        return storage.getStorage().get(fruit);
    }

    @Override
    public void update(Fruit fruit, Integer newValue) {
        storageHasThisFruit(fruit);
        storage.getStorage().replace(fruit, newValue);
    }

    @Override
    public Fruit remove() {
        return null;
    }

    private void storageHasThisFruit(Fruit fruit) {
        if (storage.getStorage().get(fruit) == null) {
            throw new RuntimeException("can not find this fruit:" + fruit + " to update it");
        }
    }
}
