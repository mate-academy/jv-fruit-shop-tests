package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new NegativeBalanceException("Negative balance is not allowed.");
        }
        FruitStorage.FRUITS.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
