package core.basesyntax.strategy.handler.impl;

import core.basesyntax.strategy.handler.OperationHandler;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public Integer operate(Integer transactionValue, Integer oldValue) {
        if (transactionValue < 0 || transactionValue == null) {
            throw new RuntimeException("Invalid transaction value: " + transactionValue);
        }
        return transactionValue;
    }
}
