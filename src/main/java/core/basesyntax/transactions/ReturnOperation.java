package core.basesyntax.transactions;

import core.basesyntax.storage.Storage;

public class ReturnOperation implements OperationHandler {
    @Override
    public void resultOfOperation(String fruitName, int amount) {
        int currentAmount = Storage.get(fruitName);
        int newAmount = currentAmount + amount;
        Storage.save(fruitName, newAmount);
    }
}
