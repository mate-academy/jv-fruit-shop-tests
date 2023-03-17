package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoSuchElementException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void evaluateTransaction(FruitTransaction transaction) {
        if (!Storage.storage.containsKey(transaction.getFruit())) {
            throw new NoSuchElementException("There no such fruits at the storage yet!");
        }
        if (transaction.getQuantity() < 0) {
            throw new TransactionQuantityException("You can't supply negative amount of fruit!");
        }
        Storage.storage.put(transaction.getFruit(),
                Storage.storage.get(transaction.getFruit()) + transaction.getQuantity());
    }
}
