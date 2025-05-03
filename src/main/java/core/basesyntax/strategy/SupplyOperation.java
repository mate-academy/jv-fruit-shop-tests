package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.ShopOperation;
import exception.OperationException;

public class SupplyOperation implements OperationHandler {
    @Override
    public void handle(ShopOperation shopOperation) {
        String fruit = shopOperation.getFruit();
        if (!Storage.fruitsStorage.containsKey(fruit)) {
            throw new OperationException("Operation is not correct, fruit doesn't exist: " + fruit);
        }
        int quantity = Storage.fruitsStorage.get(fruit);
        int suppliedQuantity = shopOperation.getQuantity();
        if (suppliedQuantity < 0) {
            throw new OperationException(
                    "Operation is not correct, quantity can't be negative. Quantity: "
                            + suppliedQuantity);
        }
        Storage.fruitsStorage.put(fruit, quantity + suppliedQuantity);
    }
}
