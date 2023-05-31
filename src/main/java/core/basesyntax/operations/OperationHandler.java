package core.basesyntax.operations;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction fruitTransaction);
}

