package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.OperationHandler;

public interface StrategyService {
    OperationHandler get(Operation operation);
}
