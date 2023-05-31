package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionHandlerService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class TransactionHandlerImpl implements TransactionHandlerService {
    private OperationStrategy operationStrategy;

    public TransactionHandlerImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void handleTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            OperationHandler operationHandler = operationStrategy.getOperationStrategy(
                    fruitTransaction.getType());
            operationHandler.handle(fruitTransaction);
        }
    }
}
