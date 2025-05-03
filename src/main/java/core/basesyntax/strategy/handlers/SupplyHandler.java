package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    public void operate(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can't be negative");
        }
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        int currentAmount = Storage.storage.get(fruitTransaction.getFruit());
        Storage.storage.put(fruitTransaction.getFruit(),currentAmount
                + fruitTransaction.getQuantity());
    }
}

