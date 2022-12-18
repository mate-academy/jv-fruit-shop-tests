package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void operate(FruitTransaction transaction) {
        if (transaction == null) {
            throw new RuntimeException("Transaction can`t be null");
        }
        int newValue = Storage.fruits.get(transaction.getFruit()) - transaction.getQuantity();
        Storage.fruits.put(transaction.getFruit(), newValue);
    }
}

