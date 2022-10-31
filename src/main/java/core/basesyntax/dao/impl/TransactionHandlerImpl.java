package core.basesyntax.dao.impl;

import core.basesyntax.dao.TransactionHandler;
import core.basesyntax.dao.exception.NoSuchAFruitAtShop;
import core.basesyntax.db.Storage;

public class TransactionHandlerImpl implements TransactionHandler {
    @Override
    public void addToBalance(String fruit, Integer quantity) {
        checkFruit(fruit);
        checkQuantity(quantity);
        if (!Storage.fruits.containsKey(fruit)) {
            Storage.fruits.put(fruit, quantity);
        } else {
            Storage.fruits.replace(fruit, Storage.fruits.get(fruit) + quantity);
        }
    }

    @Override
    public void takeFromBalance(String fruit, Integer quantity) {
        checkFruit(fruit);
        checkQuantity(quantity);
        if (!Storage.fruits.containsKey(fruit)) {
            throw new NoSuchAFruitAtShop(
                    "Can`t take fruit from shop, cause there is no such a fruit");
        } else {
            Storage.fruits.replace(fruit, Storage.fruits.get(fruit) - quantity);
        }
    }

    private void checkFruit(String fruit) {
        if (fruit == null) {
            throw new RuntimeException("Null fruit");
        }
    }

    private void checkQuantity(Integer quantity) {
        if (quantity == null) {
            throw new RuntimeException("Null quantity");
        }
    }
}
