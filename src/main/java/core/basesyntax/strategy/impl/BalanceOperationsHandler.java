package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;

public class BalanceOperationsHandler implements OperationsHandler {

    @Override
    public void useOperation(FruitTransaction transaction) {
        Storage.getStorage().put(transaction.getName(), transaction.getQuantity());
    }
}
