package core.basesyntax.service.operations.impl;

import core.basesyntax.service.operations.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public int operate(int balance, int quantity) {
        if (quantity > balance) {
            throw new RuntimeException(
                    "There are not enough funds on the balance sheet to make an operation");
        }
        return balance - quantity;
    }
}
