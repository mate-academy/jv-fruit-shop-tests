package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int previousData = Storage.getStorage().getOrDefault(transaction.getFruit(), 0);
        Storage.getStorage().put(transaction.getFruit(), previousData + transaction.getQuantity());
    }
}
