package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void execute(FruitTransaction fruitTransaction) {
        Storage.addFruits(fruitTransaction.getFruitName(), fruitTransaction.getQuantity());
    }
}
