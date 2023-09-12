package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handle(String fruit, Integer quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Balance can't be negative");
        } else if (fruit == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        Storage.getStorage().put(fruit, quantity);
    }
}
