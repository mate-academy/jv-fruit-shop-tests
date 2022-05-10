package strategy.impl;

import model.FruitTransaction;
import strategy.OperationHandler;

public class SupplyOperationImpl implements OperationHandler {
    @Override
    public int operationHandler(FruitTransaction transaction, int storeQuantity) {
        return storeQuantity + transaction.getQuantity();
    }
}
