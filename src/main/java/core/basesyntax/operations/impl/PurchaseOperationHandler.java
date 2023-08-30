package core.basesyntax.operations.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public void execute(FruitTransaction fruitTransaction) {
        String fruit = fruitTransaction.getFruitName();
        int quantity = fruitTransaction.getQuantity();
        int balance = Storage.getFruitBalance(fruit);
        if (balance < quantity) {
            throw new RuntimeException("Insufficient quantity of "
                + fruit + " in the store for purchase.");
        }
        Storage.removeFruit(fruit, quantity);
    }
}
