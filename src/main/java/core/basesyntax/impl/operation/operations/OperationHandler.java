package core.basesyntax.impl.operation.operations;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void handleTransaction(FruitTransaction fruitTransaction);
}
