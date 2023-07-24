package core.basesyntax.strategy.operation;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void evaluateTransaction(FruitTransaction transaction);
}