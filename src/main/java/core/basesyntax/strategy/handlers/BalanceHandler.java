package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Fruit can't be null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can't be negative");
        }
        Storage.storage.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
