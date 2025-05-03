package core.basesyntax.strategy.handlers;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandlers implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new RuntimeException("Fruit can`t be null");
        }
        String fruit = transaction.getFruit();
        Integer quantity = transaction.getQuantity();
        FruitStorage.storage.put(fruit, quantity);
    }
}
