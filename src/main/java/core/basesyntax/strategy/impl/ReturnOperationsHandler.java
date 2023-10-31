package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;

public class ReturnOperationsHandler implements OperationsHandler {
    @Override
    public void useOperation(FruitTransaction transaction) {
        int balance = Storage.getStorage().get(transaction.getName());
        int returnOperation = balance + transaction.getQuantity();
        Storage.getStorage().put(transaction.getName(), returnOperation);
    }
}
