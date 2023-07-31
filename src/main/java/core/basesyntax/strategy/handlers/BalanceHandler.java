package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;

public class BalanceHandler implements OperationHandler {
    @Override
    public void doOperation(String fruit, int numbers) {
        if (numbers < 0) {
            throw new RuntimeException("Balance of fruit can't be negative");
        }
        Storage.storageMap.put(fruit, numbers);
    }
}
