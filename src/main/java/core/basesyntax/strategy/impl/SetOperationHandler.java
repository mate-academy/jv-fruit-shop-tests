package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class SetOperationHandler implements OperationHandler {
    @Override
    public int operate(int oldQuantity, int newQuantity) {
        checkQuantity(oldQuantity, newQuantity);
        return newQuantity;
    }
}
