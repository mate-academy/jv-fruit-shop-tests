package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int balance = FruitStorage.fruits.get(transaction.getFruit());
        int applyQuantity = transaction.getQuantity();
        if (applyQuantity == 0) {
            throw new RuntimeException("Apply quantity can't be zero");
        }
        FruitStorage.fruits.put(transaction.getFruit(), balance + applyQuantity);
    }
}
