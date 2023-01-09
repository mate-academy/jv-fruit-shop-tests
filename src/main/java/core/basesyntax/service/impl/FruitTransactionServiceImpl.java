package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private static final OperationStrategy operationStrategy = new OperationStrategy();

    @Override
    public void update(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null || fruitTransactions.size() == 0) {
            throw new RuntimeException("Input List should contain transactions ");
        }
        for (FruitTransaction transaction : fruitTransactions) {
            OperationHandler operationHandler = operationStrategy.getHandler(transaction);
            operationHandler.handle(transaction);
        }
    }
}
