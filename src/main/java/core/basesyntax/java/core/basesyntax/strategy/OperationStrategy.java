package core.basesyntax.java.core.basesyntax.strategy;

import core.basesyntax.java.core.basesyntax.model.FruitTransaction;
import core.basesyntax.java.core.basesyntax.strategy.handler.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
