package core.basesyntax.service;

import core.basesyntax.operations.TransactionExecutor;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, TransactionExecutor> operations;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, TransactionExecutor> operations) {
        this.operations = operations;
    }

    @Override
    public TransactionExecutor get(FruitTransaction.Operation operation) {
        return operations.get(operation);
    }
}
