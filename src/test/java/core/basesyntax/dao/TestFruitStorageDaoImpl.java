package core.basesyntax.dao;

import core.basesyntax.db.TestStorage;
import core.basesyntax.model.Fruit;
import java.util.Set;

public class TestFruitStorageDaoImpl extends FruitStorageDaoImpl {
    @Override
    public boolean setBalance(Fruit fruit, int quantity) {
        TestStorage.fruits.put(fruit, quantity);
        return TestStorage.fruits.get(fruit) == quantity;
    }

    @Override
    public boolean add(Fruit fruit, int quantity) {
        checkFruitBelongToStorage(fruit);
        int oldValue = TestStorage.fruits.get(fruit);
        int newValue = oldValue + quantity;
        TestStorage.fruits.put(fruit, newValue);
        return TestStorage.fruits.get(fruit) == newValue;
    }

    @Override
    public boolean subtract(Fruit fruit, int quantity) {
        checkFruitBelongToStorage(fruit);
        int oldValue = TestStorage.fruits.get(fruit);
        int newValue = oldValue - quantity;
        TestStorage.fruits.put(fruit, newValue);
        return TestStorage.fruits.get(fruit) == newValue;
    }

    @Override
    public int getBalance(Fruit fruit) {
        checkFruitBelongToStorage(fruit);
        return TestStorage.fruits.get(fruit);
    }

    @Override
    public Set<Fruit> getAllFruits() {
        return TestStorage.fruits.keySet();
    }

    private void checkFruitBelongToStorage(Fruit fruit) {
        if (TestStorage.fruits.get(fruit) == null) {
            throw new RuntimeException("Fruit " + fruit + " doesn't belong "
                    + "to the Storage");
        }
    }
}
