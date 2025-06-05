package core.basesyntax.operation.impl;

import core.basesyntax.model.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentQuantity = FruitStorage.getStorage()
                .getOrDefault(transaction.getFruit(), 0);
        int requestedQuantity = transaction.getQuantity();
        if (currentQuantity >= requestedQuantity) {
            FruitStorage.getStorage().put(transaction.getFruit(),
                    currentQuantity - requestedQuantity);
        } else {
            throw new IllegalArgumentException("Not enough "
                    + transaction.getFruit()
                    + " in storage");
        }
    }
}
