package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class PurchaseHandler implements OperationHandler {
    @Override
    public int operate(int operationAmount, int balanceAmount) {
        return balanceAmount - operationAmount;
    }
}
