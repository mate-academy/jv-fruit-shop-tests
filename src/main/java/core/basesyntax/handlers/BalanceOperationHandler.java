package core.basesyntax.handlers;

import core.basesyntax.db.Storage;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void operate(String fruitName, int quantity) {
        checkQuantity(quantity);
        Storage.fruits.put(fruitName, quantity);
    }
}
