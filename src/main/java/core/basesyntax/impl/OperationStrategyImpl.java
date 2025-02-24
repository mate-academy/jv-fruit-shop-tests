package core.basesyntax.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.Operation;
import java.util.Map;

public class OperationStrategyImpl implements Strategy {

    @Override
    public OperationHandler get(Operation operation, Map<Operation, OperationHandler> map) {
        return map.get(operation);
    }
}
