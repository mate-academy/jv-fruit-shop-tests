package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int balance = transaction.getQuantity();
        if (balance < 0) {
            throw new RuntimeException("Balance can't be negative: " + balance);
        }
        FruitStorage.fruits.put(transaction.getFruit(), balance);
    }
}
