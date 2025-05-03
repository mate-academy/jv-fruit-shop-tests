package core.basesyntax.handlers;

import core.basesyntax.model.FruitTransaction;

public interface OperationTypeHandler {
    void handle(FruitTransaction fruitTransaction);
}
