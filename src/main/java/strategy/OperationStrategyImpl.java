package strategy;

import java.util.Map;
import model.FruitTransaction;

public class OperationStrategyImpl implements OperationStrategy {
    
    private final Map<FruitTransaction.Operation, TransactionHandler> operationHandlers;
    
    public OperationStrategyImpl(
            Map<FruitTransaction.Operation, TransactionHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }
    
    @Override
    public TransactionHandler getStrategy(FruitTransaction.Operation operationType) {
        if (operationType == null) {
            throw new IllegalArgumentException("Unknown operation: " + operationType);
        }
        return operationHandlers.get(operationType);
    }
}
