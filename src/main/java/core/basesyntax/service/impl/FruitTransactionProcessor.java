package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionProcessor {
    void processTransactions(List<FruitTransaction> transactions);
}
