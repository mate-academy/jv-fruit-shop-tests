package core.basesyntax.service.operation;

import core.basesyntax.models.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction fruitTransaction);
}
