package core.basesyntax.data.strategy;

import core.basesyntax.data.db.Storage;
import core.basesyntax.data.exeption.OperationException;
import core.basesyntax.data.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {

    @Override
    public void handle(FruitTransaction operation) {
        String fruit = operation.getFruit();
        if (!Storage.fruitsStorage.containsKey(fruit)) {
            throw new OperationException("Operation is not correct, fruit doesn't exist: " + fruit);
        }
        int quantity = Storage.fruitsStorage.get(fruit);
        int returnedQuantity = operation.getQuantity();
        if (returnedQuantity < 0) {
            throw new OperationException(
                    "Operation is not correct, quantity can't be negative. Quantity: "
                            + returnedQuantity);
        }
        Storage.fruitsStorage.put(fruit, quantity + returnedQuantity);

    }
}
