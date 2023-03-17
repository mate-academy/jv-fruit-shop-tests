package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TypeCalculatorStrategy;

public class SupplyCalculatorImpl implements TypeCalculatorStrategy {
    @Override
    public void calculate(FruitTransaction transaction) {
        if (test(transaction.getOperation())) {
            int prevFruitQuantity = Storage.storage.get(transaction.getFruit());
            Storage.storage.put(transaction.getFruit(),
                    transaction.getQuantity() + prevFruitQuantity);
            return;
        }
        throw new RuntimeException();
    }

    @Override
    public boolean test(FruitTransaction.Operation operation) {
        return FruitTransaction.Operation.SUPPLY == operation;
    }
}
