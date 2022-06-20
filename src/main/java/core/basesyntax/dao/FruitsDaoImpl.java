package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Map;

public class FruitsDaoImpl implements FruitsDao {
    private Map<String, Integer> mapWithFruits;

    public FruitsDaoImpl(Map<String, Integer> mapWithFruits) {
        this.mapWithFruits = mapWithFruits;
    }

    public FruitsDaoImpl() {
        this.mapWithFruits = Storage.fruits;
    }

    @Override
    public boolean add(String fruit, int amount) {
        fruitCheck(fruit);
        amountCheck(amount);
        mapWithFruits.put(fruit, amount);
        return true;
    }

    @Override
    public int get(String fruit) {
        fruitCheck(fruit);
        if (isFruitAvailable(fruit)) {
            return mapWithFruits.get(fruit);
        } else {
            throw new RuntimeException(fruit + "is not available at the storage");
        }
    }

    @Override
    public boolean remove(String fruit) {
        fruitCheck(fruit);
        if (isFruitAvailable(fruit)) {
            mapWithFruits.remove(fruit);
        } else {
            throw new RuntimeException(fruit + " is not available at the storage");
        }
        return true;
    }

    @Override
    public Map<String, Integer> getFruitsAndQuantityAsMap() {
        return mapWithFruits;
    }

    @Override
    public boolean isFruitAvailable(String fruit) {
        return mapWithFruits.containsKey(fruit);
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
