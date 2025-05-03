package fruitshop.operation.impl;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.operation.OperationHandler;

public class BalanceOperationHandlerImpl implements OperationHandler {
    @Override
    public void accept(FruitTransaction fruitTransaction) {
        Storage.setAmount(fruitTransaction.getFruit(), fruitTransaction.getAmount());
    }
}
