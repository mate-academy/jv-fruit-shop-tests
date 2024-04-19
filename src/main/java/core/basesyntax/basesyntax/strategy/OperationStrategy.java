package core.basesyntax.basesyntax.strategy;

import core.basesyntax.basesyntax.model.Operation;
import java.util.Map;

public class OperationStrategy {
    private Map<Operation, OperationHandler> operationMap;

    public OperationStrategy(Map<Operation, OperationHandler> operationMap) {
        this.operationMap = operationMap;
    }

    public OperationHandler getHandler(Operation operation) {
        return operationMap.get(operation);
    }
}
