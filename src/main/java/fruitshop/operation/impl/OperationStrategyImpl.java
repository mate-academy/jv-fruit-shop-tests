package fruitshop.operation.impl;

import fruitshop.model.Operation;
import fruitshop.operation.OperationHandler;
import fruitshop.operation.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Operation, OperationHandler> operations;

    public OperationStrategyImpl(Map<Operation, OperationHandler> operations) {
        this.operations = operations;
    }

    @Override
    public OperationHandler get(Operation operationType) {
        return operations.get(operationType);
    }
}
