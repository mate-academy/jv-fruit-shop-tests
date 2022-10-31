package core.basesyntax.service.process.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.process.FruitTransactionService;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private OperationStrategy operationStrategy;

    public FruitTransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            OperationHandler operationHandler = operationStrategy
                    .getOperationHandler(fruitTransaction.getOperation());
            operationHandler.doOperation(fruitTransaction.getFruit(),
                    fruitTransaction.getQuantity());
        }
    }
}
