package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;

public class BalanceTransactionImpl implements OperationHandler {
    private static final int MINIMUM_STORAGE_QUANTITY = 0;

    @Override
    public void handle(String fruit, int quantity) {
        if (fruit == null) {
            throw new RuntimeException("Balance operation failed! Incoming fruit can't be null!");
        }
        if (quantity > MINIMUM_STORAGE_QUANTITY) {
            Storage.getFruitStore().put(fruit, quantity);
        } else {
            throw new RuntimeException("Balance value can't be less than "
                    + MINIMUM_STORAGE_QUANTITY);
        }
    }
}
