package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class ReturnOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity cannot be negative: " + transaction.getQuantity());
        }
        String fruit = transaction.getFruit();
        int addedQuantity = transaction.getQuantity();
        int currentQuantity = Storage.getFruitQuantity(fruit);
        Storage.putFruit(fruit, currentQuantity + addedQuantity);
    }
}
