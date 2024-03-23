package core.basesyntax.service.strategy;

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
            throw new RuntimeException("Operation type can't be Null.");
            //i have used runtime exception because i can't imagine another naming
            // of this exception
        }
        return services.get(operationType.getCode());
    }
}
