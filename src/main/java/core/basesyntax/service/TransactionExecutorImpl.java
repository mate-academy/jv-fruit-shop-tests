package core.basesyntax.service;

import core.basesyntax.exceptions.InvalidOperationException;
import core.basesyntax.interfaces.TransactionExecutor;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationStrategy;

public class TransactionExecutorImpl implements TransactionExecutor {
    private final OperationStrategy strategy;

    public TransactionExecutorImpl(
            OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void transactionExecute(FruitTransaction transaction) {
        if (transaction == null) {
            throw new InvalidOperationException("Can't execute null!");
        }
        if (transaction.getOperation() == null) {
            throw new InvalidOperationException("Operation can't be null");
        }
        if (transaction.getFruit() == null) {
            throw new InvalidOperationException("Fruit name can't be null");
        }
        strategy.get(transaction);
    }
}
