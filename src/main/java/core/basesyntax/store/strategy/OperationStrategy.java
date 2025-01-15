package core.basesyntax.store.strategy;

import core.basesyntax.store.model.FruitTransaction;

public interface OperationStrategy {
    OperationHandler getHandler(FruitTransaction.Operation operation);
}
