package core.basesyntax.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.Operation;
import java.util.Map;

public class OperationStrategyImpl implements Strategy {
    private Map<Operation, OperationHandler> map;

    public OperationStrategyImpl(Map<Operation, OperationHandler> map) {
        this.map = map;
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        return map.get(operation);
    }
}
