package core.basesyntax.data.strategy;

import core.basesyntax.data.db.Storage;
import core.basesyntax.data.exeption.OperationException;
import core.basesyntax.data.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {

    @Override
    public void handle(FruitTransaction operation) {
        int quantity = operation.getQuantity();
        if (quantity < 0) {
            throw new OperationException(
                    "Operation is not correct, quantity can't be negative. Quantity: "
                            + operation.getQuantity());
        }
        Storage.fruitsStorage.put(operation.getFruit(), operation.getQuantity());
    }
}
