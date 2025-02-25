package core.basesyntax.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.Operation;
import java.util.Map;

public class OperationStrategyImpl implements Strategy {
    private final Map<Operation, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(Map<Operation, OperationHandler> map) {
        this.operationHandlersMap = map;
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {

        return operationHandlersMap.get(operation);
    }
}
