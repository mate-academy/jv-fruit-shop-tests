package core.basesyntax.service.impl;

import static core.basesyntax.db.Storage.fruitStorage;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class SubtractOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int quantity = fruitStorage.getOrDefault(transaction.getFruit(), 0)
                - transaction.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException("There are not enough "
                    + transaction.getFruit()
                    + " in the fruit storage!");
        }
        fruitStorage.put(transaction.getFruit(), quantity);
    }
}
