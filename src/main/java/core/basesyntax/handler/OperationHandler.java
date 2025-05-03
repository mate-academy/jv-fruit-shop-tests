package core.basesyntax.handler;

import core.basesyntax.transactor.FruitTransaction;

public interface OperationHandler {
    void operate(FruitTransaction fruitTransaction);
}
