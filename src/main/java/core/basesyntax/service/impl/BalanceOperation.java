package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.FruitStorage;

public class BalanceOperation implements OperationHandler {
    private final FruitStorage storage;

    public BalanceOperation(FruitStorage storage) {
        this.storage = storage;
    }

    @Override
    public void handle(FruitTransaction transaction) {
        int quantity = transaction.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException("Transaction error: quantity cannot be negative. Given: "
                    + quantity);
        }
        storage.updateFruitQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}
