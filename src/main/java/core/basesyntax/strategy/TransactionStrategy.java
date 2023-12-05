package core.basesyntax.strategy;

import core.basesyntax.model.Transaction;
import core.basesyntax.service.transaction.TransactionHandler;

public interface TransactionStrategy {
    TransactionHandler get(Transaction transaction);
}
