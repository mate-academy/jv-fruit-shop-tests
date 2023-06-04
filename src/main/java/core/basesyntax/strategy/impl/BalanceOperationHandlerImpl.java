package core.basesyntax.strategy.impl;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
//
public class BalanceOperationHandlerImpl implements OperationHandler {

    @Override
    public void handle(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new ValidationException("Can't make operations with null data");
        } else {
            FruitsStorage.fruitsStorage.put(fruitTransaction.getName(),
                    fruitTransaction.getQuantity());
        }
    }
}
