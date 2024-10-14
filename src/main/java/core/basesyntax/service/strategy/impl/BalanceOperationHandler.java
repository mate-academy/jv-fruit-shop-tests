package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitRecord;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitRecord transaction) {
        if (transaction == null) {
            throw new NullPointerException("Transaction cannot be null");
        }
        Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
