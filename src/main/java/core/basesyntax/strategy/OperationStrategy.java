package core.basesyntax.strategy;

import core.basesyntax.handlers.OperationTypeHandler;
import core.basesyntax.model.FruitTransaction;

public interface OperationStrategy {
    OperationTypeHandler getHandlerByOperation(FruitTransaction.Operation type);
}
