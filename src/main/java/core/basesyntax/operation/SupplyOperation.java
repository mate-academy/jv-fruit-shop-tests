package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getAmount() < 0) {
            throw new RuntimeException("Supplied amount can`t be negative");
        }
        Storage.storage.merge(fruitTransaction.getFruit(), fruitTransaction.getAmount(),
                Integer::sum);
    }
}
