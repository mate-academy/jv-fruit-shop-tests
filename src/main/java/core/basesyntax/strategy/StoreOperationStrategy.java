package core.basesyntax.strategy;

import core.basesyntax.model.StoreOperation;
import core.basesyntax.strategy.handler.OperationHandler;

public interface StoreOperationStrategy {
    OperationHandler getOperation(StoreOperation storeOperation);
}
