package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class AddingOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int quantity = fruitStorage.getOrDefault(transaction.getFruit(), 0)
                + transaction.getQuantity();
        fruitStorage.put(transaction.getFruit(), quantity);
    }
}
