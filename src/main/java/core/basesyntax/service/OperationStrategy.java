package core.basesyntax.service;

import core.basesyntax.operations.TransactionExecutor;

public interface OperationStrategy {
    TransactionExecutor get(FruitTransaction.Operation operation);
}
