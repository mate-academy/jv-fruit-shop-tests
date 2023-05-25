package strategy;

import java.util.Map;
import model.FruitTransaction;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            TransactionHandler> transactionHandlerMap) {
        this.transactionHandlerMap = transactionHandlerMap;
    }

    @Override
    public TransactionHandler get(FruitTransaction.Operation operation) {
        if (transactionHandlerMap.get(operation) == null) {
            throw new RuntimeException("Can't find handler for this operation");
        }
        return transactionHandlerMap.get(operation);
    }
}
