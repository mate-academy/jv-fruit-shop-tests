package core.basesyntax.strategy;

import core.basesyntax.model.OperationsList;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class OperationStrategy {
    private final Map<OperationsList, OperationHandler> serviceMap;

    public OperationStrategy(Map<OperationsList, OperationHandler> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public OperationHandler getOperationService(OperationsList operationsList) {
        OperationHandler operationHandler = serviceMap.get(operationsList);
        if (operationHandler == null) {
            throw new IllegalArgumentException("Unknown service type " + operationsList);
        }
        return operationHandler;
    }

    public OperationsList getOperationFromCode(String code) {
        try {
            return OperationsList.fromCode(code);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid operation code: " + code, e);
        }
    }
}
