package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int previousData = Storage.getStorage().getOrDefault(transaction.getFruit(), 0);
        if (previousData < transaction.getQuantity()) {
            throw new RuntimeException("Not enough " + transaction.getFruit() + " in storage");
        }
        Storage.getStorage().put(transaction.getFruit(), previousData - transaction.getQuantity());
    }
}
