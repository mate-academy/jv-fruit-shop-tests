package core.basesyntax.service;

import core.basesyntax.model.Transaction;

public interface TransactionExecutor {
    void executeTransaction(Transaction transaction);
}
