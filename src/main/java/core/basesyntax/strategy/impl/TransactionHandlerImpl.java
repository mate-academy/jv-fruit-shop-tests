package core.basesyntax.strategy.impl;

import core.basesyntax.db.ShopStorage;
import core.basesyntax.strategy.TransactionHandler;

public class TransactionHandlerImpl implements TransactionHandler {

    @Override
    public void addToBalance(String fruit, Integer quantity) {
        if (fruit == null) {
            throw new RuntimeException("Null fruit");
        }
        if (quantity == null) {
            throw new RuntimeException("Null quantity");
        }
        if (!ShopStorage.fruitsStorage.containsKey(fruit)) {
            ShopStorage.fruitsStorage.put(fruit, quantity);
        } else {
            ShopStorage.fruitsStorage
                    .replace(fruit, ShopStorage.fruitsStorage.get(fruit) + quantity);
        }
    }

    @Override
    public void takeFromBalance(String fruit, Integer quantity) {
        if (fruit == null) {
            throw new RuntimeException("Null fruit");
        }
        if (quantity == null) {
            throw new RuntimeException("Null quantity");
        }
        if (!ShopStorage.fruitsStorage.containsKey(fruit)) {
            throw new RuntimeException("Not found the fruit");
        } else if (ShopStorage.fruitsStorage.get(fruit) < quantity) {
            throw new RuntimeException("Quantity of fruits is not enough");
        } else {
            ShopStorage.fruitsStorage
                    .replace(fruit, ShopStorage.fruitsStorage.get(fruit) - quantity);
        }
    }
}
