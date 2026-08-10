package core.basesyntax.strategy;

import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<String, Transaction> transactionMap;

    public OperationStrategyImpl(Map<String, Transaction> transactionMap) {
        this.transactionMap = transactionMap;
    }

    @Override
    public Transaction getOperationType(String operationType) {
        Transaction transaction = transactionMap.get(operationType);
        if (transaction == null) {
            throw new IllegalArgumentException("Invalid transaction type: " + operationType);
        }
        return transaction;
    }
}
