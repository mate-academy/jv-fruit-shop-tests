package core.basesyntax.strategy.impl;

import core.basesyntax.exception.ValidationException;
import core.basesyntax.model.OperationType;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<OperationType, OperationHandler> operationTypeMap;

    public OperationStrategyImpl(Map<OperationType, OperationHandler> operationTypeMap) {
        if (operationTypeMap == null) {
            throw new ValidationException("Can't create an instance with null data");
        } else if (operationTypeMap.isEmpty()) {
            throw new ValidationException("Can't create an instance with empty data");
        } else {
            this.operationTypeMap = operationTypeMap;
        }
    }

    @Override
    public OperationHandler getOperation(OperationType operationType) {
        return operationTypeMap.get(operationType);
    }
}
