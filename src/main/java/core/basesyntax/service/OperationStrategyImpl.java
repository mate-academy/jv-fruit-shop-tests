package core.basesyntax.service;

import core.basesyntax.exceptions.ValidationException;
import core.basesyntax.service.operationhandler.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {

    private Map<OperationTypes, OperationHandler> operationHandlersMap;

    public OperationStrategyImpl(Map<OperationTypes, OperationHandler> operationHandlersMap) {
        this.operationHandlersMap = operationHandlersMap;
    }

    @Override
    public OperationHandler getOperationHandler(OperationTypes operationType)
            throws ValidationException {
        if (operationType == OperationTypes.UNKNOWN) {
            throw new ValidationException("Unknown operation type for fruit store");
        }
        return operationHandlersMap.get(operationType);
    }
}
