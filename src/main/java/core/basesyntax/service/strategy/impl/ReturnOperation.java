package core.basesyntax.service.strategy.impl;

import core.basesyntax.service.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public int getOperationType(int quantity) {
        OperationHandler.validateQuantity(quantity);
        return quantity;
    }
}
