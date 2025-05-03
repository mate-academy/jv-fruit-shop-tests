package core.basesyntax.handler;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentQuantity = Storage.getStorage().get(transaction.getFruit()) != null
                ? Storage.getStorage().get(transaction.getFruit()) : 0;
        Storage.getStorage()
                .put(transaction.getFruit(), transaction.getQuantity() + currentQuantity);
    }
}
