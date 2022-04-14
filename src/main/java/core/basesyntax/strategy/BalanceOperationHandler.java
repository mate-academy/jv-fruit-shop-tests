package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transaction) {
        int currentQuantity = transaction.getQuantitiy();
        Storage.fruitStorage.put(new Fruit(transaction.getName()), currentQuantity);
        return currentQuantity;
    }
}
