package service.operation;

import java.util.Map;
import model.Transaction;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<Transaction.Operation, OperationHandler> operationsMap;

    public OperationStrategyImpl(Map<Transaction.Operation, OperationHandler> operationsMap) {
        this.operationsMap = operationsMap;
    }

    @Override
    public OperationHandler getOperation(Transaction transaction) {
        OperationHandler handler = operationsMap.get(transaction.getOperation());
        if (handler == null) {
            throw new RuntimeException("There is no handler for transaction "
                    + transaction.getOperation());
        } else {
            return handler;
        }
    }
}
