package core.basesyntax.servise;

import core.basesyntax.servise.transaction.TransactionHandler;

public interface TransactionStrategy {
    TransactionHandler get(String operation);
}
