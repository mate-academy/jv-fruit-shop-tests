package core.basesyntax.strategy.handler.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;

public class PurchaseHandler implements OperationHandler {
    @Override
    public FruitTransaction getOperationResult(FruitTransaction fruitTransaction) {
        int fruitQuantity = fruitTransaction.getQuantity();
        fruitTransaction.setQuantity(-fruitQuantity);
        return fruitTransaction;
    }
}
