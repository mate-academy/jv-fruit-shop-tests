package service.operation;

import db.Storage;
import model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void performOperation(FruitTransaction fruitTransaction) {
        if (Storage.getAmount(fruitTransaction.getFruit()) >= fruitTransaction.getQuantity()) {
            Storage.remove(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
        } else {
            throw new IllegalArgumentException("Not enough "
                    + fruitTransaction.getFruit() + " in the store.");
        }
    }
}
