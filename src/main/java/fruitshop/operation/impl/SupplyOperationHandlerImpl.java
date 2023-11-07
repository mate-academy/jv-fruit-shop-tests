package fruitshop.operation.impl;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.operation.OperationHandler;

public class SupplyOperationHandlerImpl implements OperationHandler {
    @Override
    public void accept(FruitTransaction fruitTransaction) {
        Storage.addFruit(fruitTransaction.getFruit(), fruitTransaction.getAmount());
    }
}
