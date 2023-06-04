package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyOperationHandlerImpl implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new ValidationException("Can't make operations with null data");
        } else {
            int oldQuantity = FruitsStorage.fruitsStorage.get(fruitTransaction.getName());
            FruitsStorage.fruitsStorage.put(fruitTransaction.getName(), oldQuantity
                    + fruitTransaction.getQuantity());
        }
    }
}
