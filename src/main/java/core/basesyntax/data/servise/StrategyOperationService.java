package core.basesyntax.data.servise;

import core.basesyntax.data.model.OperationType;
import core.basesyntax.data.strategy.OperationHandler;

public interface StrategyOperationService {
    OperationHandler get(OperationType operationType);
}
