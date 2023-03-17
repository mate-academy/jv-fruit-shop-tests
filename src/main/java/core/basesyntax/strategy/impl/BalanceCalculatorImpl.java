package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TypeCalculatorStrategy;

public class BalanceCalculatorImpl implements TypeCalculatorStrategy {
    @Override
    public void calculate(FruitTransaction transaction) {
        if (test(transaction.getOperation())) {
            Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
            return;
        }
        throw new RuntimeException("Transaction type should be Balance");
    }

    @Override
    public boolean test(FruitTransaction.Operation operation) {
        return FruitTransaction.Operation.BALANCE == operation;
    }
}
