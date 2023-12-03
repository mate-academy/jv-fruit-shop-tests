package core.basesyntax.impl;

import core.basesyntax.impl.operation.OperationStrategy;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final OperationStrategy operationStrategy;

    public TransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.getOperationHandler(fruitTransaction.getOperation())
                    .handleTransaction(fruitTransaction);
        }
    }
}
