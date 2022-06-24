package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperationStrategy {
    FruitHandler get(FruitTransaction.Operation operation);
}
