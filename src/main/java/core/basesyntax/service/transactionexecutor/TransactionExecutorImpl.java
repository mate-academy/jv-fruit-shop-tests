package core.basesyntax.service.transactionexecutor;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operationstrategy.OperationStrategy;
import java.util.List;

public class TransactionExecutorImpl implements TransactionExecutor {
    private OperationStrategy operationStrategy;

    public TransactionExecutorImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void executeTransaction(List<FruitTransaction> fruitList) {
        fruitList.forEach(c -> operationStrategy
                .get(c.getOperation())
                .transaction(c));
    }
}
