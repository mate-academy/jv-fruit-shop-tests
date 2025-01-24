package core.basesyntax.transactions;

import core.basesyntax.storage.Storage;

public class BalanceOperation implements OperationHandler {
    @Override
    public void resultOfOperation(String fruitName, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(" The balance can`t be less or equals zero");
        }
        int currentAmount = Storage.get(fruitName);
        int newAmount = currentAmount + amount;
        Storage.save(fruitName, newAmount);
    }
}
