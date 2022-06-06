package core.basesyntax.dao;

import core.basesyntax.storage.Storage;

public class FruitDaoImpl implements FruitDao {
    @Override
    public Integer getQuantity(String fruitName) {
        if (Storage.fruits.containsKey(fruitName)) {
            return Storage.fruits.get(fruitName);
        } else {
            throw new RuntimeException("There is no such fruit in a store " + fruitName);
        }
    }

    @Override
    public void add(String fruitName, Integer amount) {
        if (fruitName == null || amount == null) {
            throw new RuntimeException("Value cannot be null!");
        } else if (amount < 0) {
            throw new RuntimeException("Amount value cannot be less than 0!");
        } else if (fruitName.isBlank()) {
            throw new RuntimeException("Fruit name cannot be blank!");
        }
        Storage.fruits.put(fruitName, amount);
    }
}
