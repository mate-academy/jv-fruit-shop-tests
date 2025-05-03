package core.basesyntax.service.operation;

import core.basesyntax.model.Operation;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public Operation getOperation() {
        return Operation.PURCHASE;
    }

    @Override
    public int getOperationResult(int result, int quantity) {
        if (result < quantity) {
            throw new RuntimeException("there is no needed fruit quantity in storage");
        }
        return result - quantity;
    }
}
