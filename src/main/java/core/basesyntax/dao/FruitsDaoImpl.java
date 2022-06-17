package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitsDaoImpl implements FruitsDao {
    @Override
    public boolean add(String fruit, int amount) {
        fruitCheck(fruit);
        amountCheck(amount);
        Storage.fruits.put(fruit, amount);
        return true;
    }

    @Override
    public int get(String fruit) {
        fruitCheck(fruit);
        if (isFruitAvailable(fruit)) {
            return Storage.fruits.get(fruit);
        } else {
            throw new RuntimeException(fruit + "is not available at the storage");
        }
    }

    @Override
    public boolean remove(String fruit) {
        fruitCheck(fruit);
        if (isFruitAvailable(fruit)) {
            Storage.fruits.remove(fruit);
        } else {
            throw new RuntimeException(fruit + " is not available at the storage");
        }
        return true;
    }

    @Override
    public Map<String, Integer> getFruitsAndQuantityAsMap() {
        return Storage.fruits;
    }

    @Override
    public boolean isFruitAvailable(String fruit) {
        return Storage.fruits.containsKey(fruit);
    }

    private void amountCheck(int amount) {
        if (amount < 0) {
            throw new RuntimeException("Amount can't be less than zero!");
        }
    }

    private void fruitCheck(String fruit) {
        if (fruit == null) {
            throw new RuntimeException("Fruit can't be null");
        }
    }
}
