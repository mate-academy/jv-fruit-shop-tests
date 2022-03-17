package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;

public interface TransactionService {
    void doOperation(OperationStrategy operationStrategy,
                     FruitTransaction fruitTransaction);
}
