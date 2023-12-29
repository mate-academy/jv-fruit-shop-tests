package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandlerOperation implements OperationHandler {

    @Override
    public void handleTransaction(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new RuntimeException(
                    "Fruit can't be null!"
            );
        }
        int currentValue = Storage.fruits.get(transaction.getFruit());
        Storage.fruits.put(transaction.getFruit(),
                currentValue + transaction.getQuantity());
    }
}
