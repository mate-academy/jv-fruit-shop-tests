package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    boolean makeOperation(FruitTransaction transaction);
}
