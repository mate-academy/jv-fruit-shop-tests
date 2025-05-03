package strategy.impl;

import java.util.Map;
import model.Operation;
import strategy.OperationHandler;
import strategy.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<Operation, OperationHandler> operationHandlers;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    @Override
    public OperationHandler getOperationHandler(Operation operation) {
        if (operation == null) {
            throw new RuntimeException("Can't get Operation Handler from null operation");
        }
        return operationHandlers.get(operation);
    }
}
