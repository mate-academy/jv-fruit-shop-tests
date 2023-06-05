package core.basesyntax.strategy;

import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;

public interface Strategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
