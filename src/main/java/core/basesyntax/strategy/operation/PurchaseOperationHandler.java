package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.NoSuchElementException;
import core.basesyntax.exceptions.NullDataException;
import core.basesyntax.exceptions.TransactionQuantityException;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void evaluateTransaction(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullDataException("Transaction is null!");
        }
        if (!Storage.storage.containsKey(transaction.getFruit())) {
            throw new NoSuchElementException("There no such fruits at the storage yet!");
        }
        int amountInStorage = Storage.storage.get(transaction.getFruit());
        if (amountInStorage < transaction.getQuantity()) {
            throw new TransactionQuantityException("Can't purchase more than what is in storage!");
        }
        if (transaction.getQuantity() < 0) {
            throw new TransactionQuantityException("Can't purchase negative amount of fruit!");
        }
        Storage.storage.put(transaction.getFruit(), amountInStorage - transaction.getQuantity());
    }
}
