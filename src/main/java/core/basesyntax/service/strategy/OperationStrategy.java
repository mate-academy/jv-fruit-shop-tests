package core.basesyntax.service.strategy;

import core.basesyntax.exceptions.NullOperationTypeException;
import core.basesyntax.models.Operation;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class OperationStrategy {
    private final Map<String, OperationHandler> services;

    public OperationStrategy(Map<String, OperationHandler> services) {
        this.services = services;
    }

    public OperationHandler getOperation(Operation operationType) {
        if (operationType == null) {
            throw new NullOperationTypeException("Operation type can't be Null.");
        }
        return services.get(operationType.getCode());
    }
}
