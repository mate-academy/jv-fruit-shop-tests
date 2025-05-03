package core.basesyntax.strategy.transactionhandlers;

import core.basesyntax.model.FruitTransaction;

public interface TransactionHandler {
    void transaction(FruitTransaction transaction);
}
