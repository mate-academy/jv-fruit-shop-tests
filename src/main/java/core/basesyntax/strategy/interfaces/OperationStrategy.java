package core.basesyntax.strategy.interfaces;

import core.basesyntax.OperationType;

public interface OperationStrategy {
    OperationHandler getStrategy(OperationType type);
}
