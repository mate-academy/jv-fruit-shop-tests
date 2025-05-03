package core.basesyntax.strategy.operationstrategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.transactionhandlers.TransactionHandler;

public interface OperationStrategy {
    TransactionHandler get(FruitTransaction.Operation operation);
}
