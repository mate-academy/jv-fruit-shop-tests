package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandlerImpl implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new ValidationException("Can't make operations with null data");
        } else {
            int oldQuantity = FruitsStorage.fruitsStorage.get(fruitTransaction.getName());
            if (oldQuantity < fruitTransaction.getQuantity()) {
                throw new RuntimeException("Not enough " + fruitTransaction.getName()
                        + " for purchase!");
            }
            FruitsStorage.fruitsStorage.put(fruitTransaction.getName(),
                    oldQuantity - fruitTransaction.getQuantity());
        }
    }
}
