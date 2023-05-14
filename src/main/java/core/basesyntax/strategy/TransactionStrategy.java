package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;

public interface TransactionStrategy {
    void handleOperation(Transaction transaction);
}
