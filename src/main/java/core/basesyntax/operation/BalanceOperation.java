package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative. Provided: "
                    + transaction.getQuantity());
        }
        Storage.put(transaction.getFruit(), transaction.getQuantity());
    }
}
