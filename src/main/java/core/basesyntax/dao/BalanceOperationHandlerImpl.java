package core.basesyntax.dao;

import core.basesyntax.db.Storage;

public class BalanceOperationHandlerImpl implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity) {
        if (fruit == null || quantity < 0) {
            throw new RuntimeException("Invalid input parameters.");
        }
        Storage.fruitStorage.put(fruit, quantity);
    }
}
