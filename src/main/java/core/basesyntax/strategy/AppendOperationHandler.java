package core.basesyntax.strategy;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;

public class AppendOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transaction) {
        Fruit fruit = new Fruit(transaction.getName());
        int currentQuantity = Storage.fruitStorage
                .getOrDefault(new Fruit(transaction.getName()), 0);
        Storage.fruitStorage.put(fruit, currentQuantity + transaction.getQuantitiy());
        return currentQuantity + transaction.getQuantitiy();
    }
}
