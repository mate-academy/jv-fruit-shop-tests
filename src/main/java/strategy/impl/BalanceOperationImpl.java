package strategy.impl;

import db.Storage;
import model.FruitTransaction;
import strategy.OperationHandler;

public class BalanceOperationImpl implements OperationHandler {
    @Override
    public void handler(FruitTransaction fruitTransaction) {
        if (!fruitTransaction.getOperation().equals(FruitTransaction.Operation.BALANCE)) {
            throw new RuntimeException("Operation is not correct for balance");
        } else {
            Storage.map.put(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        }
    }
}
