package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationService;

public class BalanceOperation implements OperationService {
    @Override
    public void doOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0 || fruitTransaction.getFruit() == null) {
            throw new RuntimeException("Not correct value");
        }
        Storage.getStorage().put(fruitTransaction.getFruit(),fruitTransaction.getQuantity());
    }
}
