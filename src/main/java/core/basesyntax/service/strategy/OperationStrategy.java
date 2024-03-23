package core.basesyntax.service.strategy;

import core.basesyntax.exceptions.ServiceNotExistsException;
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
        OperationHandler service = services.get(operationType.getCode());
        if (service == null) { // to be true Unreachable statement, when we use this enum
            throw new ServiceNotExistsException("There is no such service implemented: "
                    + operationType.name());
        }
        return service;
    }
}
