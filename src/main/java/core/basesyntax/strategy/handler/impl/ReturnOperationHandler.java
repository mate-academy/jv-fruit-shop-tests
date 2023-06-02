package core.basesyntax.strategy.handler.impl;

import core.basesyntax.strategy.handler.OperationHandler;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public int operate(int transaction, int oldValue) {
        if (transaction < 0) {
            throw new RuntimeException("Transaction cannot be less than zero: " + transaction);
        }
        return oldValue + transaction;
    }
}
