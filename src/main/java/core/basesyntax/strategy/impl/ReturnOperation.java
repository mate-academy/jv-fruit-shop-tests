package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.OperationHandler;

public class ReturnOperation implements OperationHandler {

    @Override
    public void doTransaction(String fruitName, int quantity) {
        if (quantity > 0) {
            Storage.addFruitQuantity(fruitName, quantity);
        }
    }
}
