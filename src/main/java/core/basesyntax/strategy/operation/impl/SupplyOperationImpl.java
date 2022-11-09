package core.basesyntax.strategy.operation.impl;

import core.basesyntax.strategy.operation.OperationHandler;

public class SupplyOperationImpl implements OperationHandler {
    @Override
    public void applyOperation(String fruitName, int value) {
        int oldValue = getBalanceFromFruitName(fruitName);
        int newValue = oldValue + value;
        putNewBalanceToFruit(fruitName, newValue);
    }
}
