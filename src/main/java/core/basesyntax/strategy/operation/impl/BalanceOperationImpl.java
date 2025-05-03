package core.basesyntax.strategy.operation.impl;

import core.basesyntax.strategy.operation.OperationHandler;

public class BalanceOperationImpl implements OperationHandler {
    @Override
    public void applyOperation(String fruitName, int value) {
        if (getBalanceFromFruitName(fruitName) == null) {
            putNewBalanceToFruit(fruitName, value);
            return;
        }
        int oldValue = getBalanceFromFruitName(fruitName);
        int newValue = oldValue + value;
        putNewBalanceToFruit(fruitName, newValue);
    }
}
