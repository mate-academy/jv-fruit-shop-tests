package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;

public class ReturnHandler implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new NullPointerException("FruitTransaction cannot be null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new IllegalArgumentException("FruitTransaction quantity cannot be negative");
        }
        int oldQuantity = Storage.storage.getOrDefault(fruitTransaction.getFruit(), 0);
        Storage.storage.put(fruitTransaction.getFruit(),
                oldQuantity + fruitTransaction.getQuantity());
    }
}
