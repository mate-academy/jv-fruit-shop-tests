package core.basesyntax.strategy.activities;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void executeTransaction(FruitTransaction transaction);
}
