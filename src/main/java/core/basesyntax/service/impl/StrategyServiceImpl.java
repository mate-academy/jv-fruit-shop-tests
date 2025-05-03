package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.StrategyService;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class StrategyServiceImpl implements StrategyService {
    private Map<Operation, OperationHandler> operationHandlerMap;

    public StrategyServiceImpl(Map<Operation,
            OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Operation can't be null");
        }
        return operationHandlerMap.get(operation);
    }
}
