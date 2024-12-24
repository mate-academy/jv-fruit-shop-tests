package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public class PurchaseOperation implements OperationHandler {
    public void handle(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        if (fruit == null || !Storage.getAll().containsKey(fruit)) {
            throw new RuntimeException("Such fruit is not sold here: " + fruit);
        }

        int quantity = transaction.getQuantity();
        int currentBalance = Storage.getAll().get(transaction.getFruit());

        if (currentBalance - quantity < 0) {
            throw new RuntimeException("Insufficient quantity of fruit: " + fruit
                    + ". Current balance: " + currentBalance);
        }

        Storage.subtract(fruit, quantity);
    }
}
