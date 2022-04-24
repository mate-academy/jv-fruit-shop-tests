package basesyntax.service.strategy.handlers;

import basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void doOperation(FruitTransaction fruitTransaction);
}
