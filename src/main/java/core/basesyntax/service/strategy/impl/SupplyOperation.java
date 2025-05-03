package core.basesyntax.service.strategy.impl;

import core.basesyntax.service.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public int getOperationType(int quantity) {
        OperationHandler.validateQuantity(quantity);
        return quantity;
    }
}
