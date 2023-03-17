package core.basesyntax.strategy.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.TransactionStrategy;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<Transaction.Operation, OperationHandler> operationsMap;

    public TransactionStrategyImpl(Map<Transaction.Operation,
            OperationHandler> operationsMap) {
        this.operationsMap = operationsMap;
    }

    @Override
    public OperationHandler get(Transaction.Operation operation) {
        return operationsMap.get(operation);
    }
}
