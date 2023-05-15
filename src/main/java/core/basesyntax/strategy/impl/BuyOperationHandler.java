package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class BuyOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int oldQuantity = Storage.fruit.get(transaction.getFruit());
        if (oldQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Not enough " + transaction.getFruit() + " for purchase!");
        }
        Storage.fruit.put(transaction.getFruit(), oldQuantity - transaction.getQuantity());

    }
}
