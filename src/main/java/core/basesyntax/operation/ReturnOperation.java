package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperation implements OperationHandler {
    @Override
    public void processOperation(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getAmount() < 0) {
            throw new RuntimeException("Returned amount can`t be negative");
        }
        Storage.storage.merge(fruitTransaction.getFruit(), fruitTransaction.getAmount(),
                Integer::sum);
    }
}
