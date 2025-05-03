package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class FruitDaoImpl implements FruitDao {

    @Override
    public Integer getQuantity(String fruitName) {
        if (Storage.fruits.containsKey(fruitName)) {
            return Storage.fruits.get(fruitName);
        } else {
            throw new RuntimeException("There's no such a fruit in a Storage: " + fruitName);
        }
    }

    @Override
    public void update(String fruitName, Integer amount) {
        if (fruitName == null || amount == null) {
            throw new RuntimeException("Can't update null values");
        } else if (amount < 0) {
            throw new RuntimeException("The quantity of a fruit can't be negative");
        }
        Storage.fruits.put(fruitName, amount);
    }
}
