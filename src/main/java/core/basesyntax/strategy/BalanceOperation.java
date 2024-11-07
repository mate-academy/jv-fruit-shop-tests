package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.ShopOperation;
import exception.OperationException;

public class BalanceOperation implements OperationHandler {
    @Override
    public void handle(ShopOperation shopOperation) {
        int quantity = shopOperation.getQuantity();
        if (quantity < 0) {
            throw new OperationException(
                    "Operation is not correct, quantity can't be negative. Quantity: "
                            + shopOperation.getQuantity());
        }
        Storage.fruitsStorage.put(shopOperation.getFruit(), shopOperation.getQuantity());
    }
}
