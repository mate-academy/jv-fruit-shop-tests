package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.NoSuchElementException;

public class FruitImplemDao implements DaoFruit {
    @Override
    public void addFruits(String fruit, int amount) {
        if (amount == 0) {
            throw new RuntimeException("Ammount of added fruit cannot be 0");
        }
        Storage.fruits.put(fruit, amount);
    }

    @Override
    public int getAmountOfFruit(String fruit) {
        if (!Storage.fruits.containsKey(fruit)) {
            throw new NoSuchElementException("There is no " + fruit + " in storage");
        }
        return Storage.fruits.get(fruit);
    }
}
