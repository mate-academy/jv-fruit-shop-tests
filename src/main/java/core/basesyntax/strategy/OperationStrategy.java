package core.basesyntax.strategy;

import core.basesyntax.fruittransaction.FruitTransaction;
import core.basesyntax.transactionexecutor.OperationHandler;

public interface OperationStrategy {
    OperationHandler get(FruitTransaction.Operation operation);
}
