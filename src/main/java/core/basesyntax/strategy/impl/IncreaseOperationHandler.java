package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class IncreaseOperationHandler implements OperationHandler {
    @Override
    public int operate(int oldQuantity, int newQuantity) {
        checkQuantity(oldQuantity, newQuantity);
        return oldQuantity + newQuantity;
    }
}
