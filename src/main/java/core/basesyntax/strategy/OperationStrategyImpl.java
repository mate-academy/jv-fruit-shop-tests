package core.basesyntax.strategy;

import core.basesyntax.model.Operation;
import core.basesyntax.strategy.operations.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static final String SEPARATOR = ",";
    private static final int OPERATION_INDEX = 0;
    private static final String NOT_EXISTING_OPERATION_MESSAGE = "There is no such operation";
    private final Map<Operation, OperationHandler> operationHandlerMap;

    public OperationStrategyImpl(Map<Operation,
            OperationHandler> operationOperationHandlerMap) {
        this.operationHandlerMap = operationOperationHandlerMap;
    }

    @Override
    public OperationHandler getHandler(String line) {
        String type = line.split(SEPARATOR)[OPERATION_INDEX];
        Operation operation = Operation.getOperationFromString(type);
        if (operation != null) {
            return operationHandlerMap.get(Operation.getOperationFromString(type));
        }
        throw new RuntimeException(NOT_EXISTING_OPERATION_MESSAGE);
    }
}
