package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int balance = FruitStorage.fruits.get(transaction.getFruit());
        int returnQuantity = transaction.getQuantity();
        if (returnQuantity == 0) {
            throw new RuntimeException("Return quantity can't be zero");
        }
        FruitStorage.fruits.put(transaction.getFruit(), balance + returnQuantity);
    }
}
