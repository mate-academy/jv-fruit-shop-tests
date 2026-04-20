package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Transaction.Operation, OperationHandler> handlers;
    
    public OperationStrategyImpl(Map<Transaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler getOperationHandler(Transaction.Operation operation) {
        OperationHandler handler = handlers.get(operation);
        
        if (handler == null) {
            throw new IllegalStateException("No handler for operation " + operation);
        }
        
        if (handlers.isEmpty()) {
            throw new RuntimeException("There are no any handlers added");
        }
        
        return handlers.get(operation);
    }
}
