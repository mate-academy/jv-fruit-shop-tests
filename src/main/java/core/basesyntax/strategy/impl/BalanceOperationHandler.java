package core.basesyntax.strategy.impl;

import static java.util.Objects.isNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void applyChanges(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getNameOfFruit();
        if (isNull(fruitName)) {
            throw new RuntimeException("Fruits name cannot be null");
        }
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Numbers of fruits cannot be negative");
        }
        if (Storage.storage.get(fruitName) != null) {
            throw new RuntimeException("Fruit" + fruitName + "is already on the balance");
        }
        Storage.storage.put(fruitTransaction.getNameOfFruit(),fruitTransaction.getQuantity());
    }
}
