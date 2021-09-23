package core.basesyntax.service;

import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.Handler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<Operation, Handler> handlerMap;

    public OperationStrategyImpl(Map<Operation, Handler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public Handler getHandler(Operation type) {
        if (handlerMap.get(type) == null) {
            throw new RuntimeException("Invalid operation");
        }
        return handlerMap.get(type);
    }
}
