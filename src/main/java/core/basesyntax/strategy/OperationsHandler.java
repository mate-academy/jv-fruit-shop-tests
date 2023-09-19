package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperationsHandler {
    void useOperation(FruitTransaction transaction);
}
