package operation.impl;

import db.Storage;
import model.FruitTransaction;
import operation.OperationHandler;

public class PurchaseOperationHandlerImpl implements OperationHandler {

    @Override
    public void accept(FruitTransaction fruitTransaction) {
        if (Storage.getFruitsAndAmount().get(fruitTransaction.getFruit())
                >= fruitTransaction.getAmount()) {
            Storage.addFruit(fruitTransaction.getFruit(), -fruitTransaction.getAmount());
        }
    }
}
