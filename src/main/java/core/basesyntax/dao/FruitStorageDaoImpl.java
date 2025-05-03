package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Set;

public class FruitStorageDaoImpl implements FruitStorageDao {
    private Storage storage;

    public FruitStorageDaoImpl(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean setBalance(Fruit fruit, int quantity) {
        storage.getFruits().put(fruit, quantity);
        return storage.getFruits().get(fruit) == quantity;
    }

    @Override
    public boolean add(Fruit fruit, int quantity) {
        checkFruitBelongToStorage(fruit);
        int oldValue = storage.getFruits().get(fruit);
        int newValue = oldValue + quantity;
        storage.getFruits().put(fruit, newValue);
        return storage.getFruits().get(fruit) == newValue;
    }

    @Override
    public boolean subtract(Fruit fruit, int quantity) {
        checkFruitBelongToStorage(fruit);
        int oldValue = storage.getFruits().get(fruit);
        int newValue = oldValue - quantity;
        storage.getFruits().put(fruit, newValue);
        return storage.getFruits().get(fruit) == newValue;
    }

    @Override
    public int getBalance(Fruit fruit) {
        checkFruitBelongToStorage(fruit);
        return storage.getFruits().get(fruit);
    }

    @Override
    public Set<Fruit> getAllFruits() {
        return storage.getFruits().keySet();
    }

    private void checkFruitBelongToStorage(Fruit fruit) {
        if (storage.getFruits().get(fruit) == null) {
            throw new RuntimeException("Fruit " + fruit + " doesn't belong "
                    + "to the Storage");
        }
    }
}
