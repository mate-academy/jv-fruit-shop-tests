package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;

public interface TransactionStrategy {
    OperationHandler get(Transaction.Operation operation);
}
