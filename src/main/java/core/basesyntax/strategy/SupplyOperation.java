package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public int apply(int currentQuantity, int transactionQuantity) {
        if (transactionQuantity < 0) {
            throw new RuntimeException("Supply quantity cannot be negative: " + transactionQuantity);
        }
        return currentQuantity + transactionQuantity;
    }
}
