package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TypeCalculatorStrategy;

public class PurchaseCalculatorImpl implements TypeCalculatorStrategy {
    @Override
    public void calculate(FruitTransaction transaction) {
        if (test(transaction.getOperation())) {
            int prevFruitQuantity = Storage.storage.get(transaction.getFruit());
            if (prevFruitQuantity >= transaction.getQuantity()) {
                Storage.storage.put(transaction.getFruit(),
                        prevFruitQuantity - transaction.getQuantity());
            }
            return;
        }
        throw new RuntimeException("Transaction type should be Purchase");
    }

    @Override
    public boolean test(FruitTransaction.Operation operation) {
        return FruitTransaction.Operation.PURCHASE == operation;
    }
}
