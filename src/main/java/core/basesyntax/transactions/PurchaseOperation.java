package core.basesyntax.transactions;

import core.basesyntax.storage.Storage;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void resultOfOperation(String fruitName, int amount) {
        int currentAmount = Storage.get(fruitName);
        int newAmount = currentAmount - amount;
        if (newAmount <= 0) {
            throw new RuntimeException("New amount can`t be negative");
        }
        Storage.save(fruitName, newAmount);
    }
}
