package core.basesyntax.strategy.handler.impl;

import core.basesyntax.strategy.handler.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {

    @Override
    public Integer operate(Integer transactionValue, Integer oldValue) {
        if (transactionValue == null || transactionValue < 0) {
            throw new RuntimeException(" Transaction Value is invalid");
        }
        return oldValue + transactionValue;
    }
}
