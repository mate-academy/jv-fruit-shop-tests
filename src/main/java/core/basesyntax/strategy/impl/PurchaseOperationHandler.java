package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() > Storage.fruits.get(transaction.getFruit())) {
            throw new RuntimeException("Not enough fruit for this operation");
        }
        Storage.fruits.put(transaction.getFruit(),
                Storage.fruits.containsKey(transaction.getFruit())
                ? Storage.fruits.get(transaction.getFruit()) - transaction.getQuantity()
                : transaction.getQuantity());
    }
}
