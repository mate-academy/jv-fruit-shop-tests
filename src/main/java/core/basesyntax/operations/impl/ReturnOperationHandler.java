package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void execute(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruitName();
        Storage.addFruits(fruit, Storage.getFruitBalance(fruit) + fruitTransaction.getQuantity());
    }
}
