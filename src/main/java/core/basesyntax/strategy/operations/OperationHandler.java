package core.basesyntax.strategy.operations;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void handler(FruitTransaction fruitTransaction);
}
