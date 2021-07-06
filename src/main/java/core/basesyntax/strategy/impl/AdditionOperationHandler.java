package core.basesyntax.strategy.impl;

import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;

public class AdditionOperationHandler implements OperationHandler {
    @Override
    public int apply(Transaction transaction) {
        Fruit fruit = new Fruit(transaction.getName());
        int quantity = Storage.storage.getOrDefault(fruit, 0) + transaction.getQuantity();
        Storage.storage.put(fruit, quantity);
        return quantity;
    }
}

