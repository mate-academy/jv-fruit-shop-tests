package core.basesyntax.strategy.handler;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionDao;

public abstract class OperationHandler {
    protected final TransactionDao transactionDao;

    public OperationHandler(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    public abstract void handle(Transaction transaction);
}
