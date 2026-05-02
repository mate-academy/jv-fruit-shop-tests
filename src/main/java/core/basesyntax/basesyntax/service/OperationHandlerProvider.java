package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.FruitTransaction;
import core.basesyntax.basesyntax.strategy.OperationHandler;

public interface OperationHandlerProvider {
    OperationHandler get(FruitTransaction.Operation operation);
}
