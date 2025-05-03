package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.fruitscontent.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandlerImpl implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction) {
        int oldQuantity = FruitStorage.fruits.get(transaction.getFruit());
        if (oldQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Not enough " + transaction.getFruit() + " for purchase!");
        }
        FruitStorage.fruits.put(transaction.getFruit(), oldQuantity - transaction.getQuantity());
    }
}
