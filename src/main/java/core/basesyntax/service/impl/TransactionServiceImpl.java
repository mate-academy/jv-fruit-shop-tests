package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationStrategy;

public class TransactionServiceImpl implements TransactionService {
    @Override
    public void doOperation(OperationStrategy operationStrategy,
                            FruitTransaction fruitTransaction) {
        operationStrategy.process(fruitTransaction.getOperationType(),
                fruitTransaction.getFruit());
    }
}
