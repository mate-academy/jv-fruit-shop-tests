package basesyntax.strategy;

import basesyntax.model.FruitTransaction;

public interface OperationStrategy {
    OperationHandler getOperationType(FruitTransaction.Operation operation);
}
