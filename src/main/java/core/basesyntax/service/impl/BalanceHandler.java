package core.basesyntax.service.impl;

import core.basesyntax.service.OperationHandler;

public class BalanceHandler implements OperationHandler {
    @Override
    public int operate(int operationAmount, int balanceAmount) {
        if (operationAmount < 0) {
            throw new RuntimeException("Balance amount is less than 0, please check your file");
        }
        return operationAmount + balanceAmount;
    }
}
