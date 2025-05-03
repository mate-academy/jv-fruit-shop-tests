package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;
import core.basesyntax.operations.OperationHandler;

public interface Strategy {
    OperationHandler getOperationHandler(Transaction transaction);
}
