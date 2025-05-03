package core.basesyntax.strategy.impl;

import core.basesyntax.strategy.OperationHandler;

public class DecreaseOperationHandler implements OperationHandler {
    @Override
    public int operate(int oldQuantity, int newQuantity) {
        checkQuantity(oldQuantity, newQuantity);
        if (oldQuantity < newQuantity) {
            throw new RuntimeException(getPurchaseErrorMessage(oldQuantity, newQuantity));
        }
        return oldQuantity - newQuantity;
    }

    private String getPurchaseErrorMessage(int oldQuantity, int newQuantity) {
        return String.format("Can't buy %d. Only %d fruits are available in stock",
                newQuantity, oldQuantity);
    }
}
