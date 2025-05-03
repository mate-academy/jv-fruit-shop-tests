package core.basesyntax.operationservice;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;

public class BalanceOperationImpl implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        if (quantity < 0) {
            throw new IllegalArgumentException("Error: negative amount " + quantity);
        }

        Storage.putFruit(fruit, quantity);
    }
}
