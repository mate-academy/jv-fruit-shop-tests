package core.basesyntax.handlers;

import core.basesyntax.db.Storage;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void operate(String fruit, int quantity) {
        checkQuantity(quantity);
        int quantityAfterReturn = Storage.fruits.get(fruit) + quantity;
        Storage.fruits.put(fruit, quantityAfterReturn);
    }
}
