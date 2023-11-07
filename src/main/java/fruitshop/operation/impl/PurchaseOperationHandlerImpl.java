package fruitshop.operation.impl;

import fruitshop.db.Storage;
import fruitshop.model.FruitTransaction;
import fruitshop.operation.OperationHandler;

public class PurchaseOperationHandlerImpl implements OperationHandler {

    @Override
    public void accept(FruitTransaction fruitTransaction) {
        if (Storage.getFruitsAndAmount().get(fruitTransaction.getFruit())
                >= fruitTransaction.getAmount()) {
            Storage.addFruit(fruitTransaction.getFruit(), -fruitTransaction.getAmount());
        }
    }
}
