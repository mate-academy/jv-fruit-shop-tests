package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;

public interface TransactionProcessor {
    public void processTransaction(FruitTransaction transaction);
}
