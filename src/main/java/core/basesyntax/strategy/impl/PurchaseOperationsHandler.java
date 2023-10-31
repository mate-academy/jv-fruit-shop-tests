package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationsHandler;

public class PurchaseOperationsHandler implements OperationsHandler {
    @Override
    public void useOperation(FruitTransaction transaction) {
        int balance = Storage.getStorage().get(transaction.getName());
        int purchase = balance - transaction.getQuantity();
        if (purchase < 0) {
            throw new RuntimeException("User can't buy more than " + balance);
        }
        Storage.getStorage().put(transaction.getName(), purchase);
    }
}
