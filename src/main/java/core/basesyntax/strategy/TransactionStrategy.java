package core.basesyntax.strategy;

import core.basesyntax.servise.transaction.TransactionHandler;

public interface TransactionStrategy {
    TransactionHandler get(String operation);
}
