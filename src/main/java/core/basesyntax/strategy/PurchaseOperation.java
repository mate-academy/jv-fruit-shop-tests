package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperation implements OperationHandler {
    @Override
    public int apply(int currentQuantity, int transactionQuantity) {
        if (transactionQuantity < 0) {
            throw new RuntimeException("Purchase quantity cannot be negative: " + transactionQuantity);
        }
        if (currentQuantity < transactionQuantity) {
            throw new RuntimeException("Cannot purchase " + transactionQuantity + " units, only " + currentQuantity + " available.");
        }
        return currentQuantity - transactionQuantity;
    }
}
