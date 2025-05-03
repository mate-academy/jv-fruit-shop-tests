package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class OperationHandlerIn implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction) {
        int value = 0;
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Balance can`t be less then zero");
        }

        if (Storage.storage.get(transaction.getFruit()) != null) {
            value = Storage.storage.get(transaction.getFruit());
        }
        Storage.storage.put(transaction.getFruit(), value
                + transaction.getQuantity());
    }
}
