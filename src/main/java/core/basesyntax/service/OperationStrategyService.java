package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;

public interface OperationStrategyService {
    OperationHandler getOperationHandler(FruitTransaction.Operation operation);
}
