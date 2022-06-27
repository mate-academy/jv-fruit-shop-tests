package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import java.util.Map;

public class OperationStrategy {
    private static Map<Operation, OperationHandler> operationHandler;

    public static void setOperationHandler(Map<Operation, OperationHandler> operationHandler) {
        OperationStrategy.operationHandler = operationHandler;
    }

    public static OperationHandler getOperationServiceStrategy(Operation operation) {
        return operationHandler.get(operation);
    }
}
