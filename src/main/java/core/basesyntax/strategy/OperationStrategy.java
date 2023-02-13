package core.basesyntax.strategy;

import core.basesyntax.fruitscontent.FruitTransaction;

public interface OperationStrategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
