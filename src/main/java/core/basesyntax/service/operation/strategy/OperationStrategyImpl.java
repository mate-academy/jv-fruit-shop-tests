package core.basesyntax.service.operation.strategy;

import core.basesyntax.service.operation.handler.OperationTypeHandler;
import core.basesyntax.service.operation.handler.Operations;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {

    private Map<Operations, OperationTypeHandler> operationTypeHandlerMap;

    public OperationStrategyImpl(Map<Operations, OperationTypeHandler> operationTypeHandlerMap) {
        this.operationTypeHandlerMap = operationTypeHandlerMap;
    }

    @Override
    public OperationTypeHandler get(Operations operation) {
        return operationTypeHandlerMap.get(operation);
    }
}
