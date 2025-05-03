package core.basesyntax.model.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Invalid quantity: " + transaction.getQuantity());
        }
        Storage.getStorage().put(transaction.getFruit(), transaction.getQuantity());
    }
}
