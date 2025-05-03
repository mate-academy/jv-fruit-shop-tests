package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;

public interface OperationHandlerStrategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
