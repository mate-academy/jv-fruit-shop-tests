package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public int operate(int operationAmount, int balanceAmount) {
        return operationAmount + balanceAmount;
    }
}
