package core.basesyntax.strategy;

import core.basesyntax.transaction.TransactionHandler;

public interface TransactionStrategy {
    TransactionHandler get(String operation);
}
