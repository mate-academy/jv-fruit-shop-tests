package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void operationProcess(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity can't be less than 0");
        }
        Storage.fruitsStorage.put(fruitTransaction.getFruit(),fruitTransaction.getQuantity());
    }
}
