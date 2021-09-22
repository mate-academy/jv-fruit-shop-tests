package core.basesyntax.service.oparation;

import core.basesyntax.model.TypeOperations;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<String, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(Map<String, OperationHandler> operationHandlersMap) {
        this.operationHandlersMap = operationHandlersMap;
    }

    @Override
    public OperationHandler get(TypeOperations typeOperations) {
        return operationHandlersMap.get(typeOperations.get());
    }
}
