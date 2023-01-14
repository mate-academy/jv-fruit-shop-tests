package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public interface OperationStrategy {
    OperationHandler getHandlerByOperation(FruitTransaction.Operation operation);
}
