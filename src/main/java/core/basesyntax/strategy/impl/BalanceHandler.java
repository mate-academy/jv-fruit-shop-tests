package core.basesyntax.strategy.impl;

import core.basesyntax.database.Storage;
import core.basesyntax.strategy.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public void doTransaction(String fruit, int value) {
        Storage.STORAGE.put(fruit, value);
    }
}
