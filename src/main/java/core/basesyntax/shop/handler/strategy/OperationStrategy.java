package core.basesyntax.shop.handler.strategy;

import core.basesyntax.shop.handler.OperationHandler;
import core.basesyntax.shop.model.OperationType;

public interface OperationStrategy {
    OperationHandler getOperationHandler(OperationType operationType);
}
