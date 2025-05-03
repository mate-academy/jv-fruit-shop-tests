package core.basesyntax.basesyntax.service;

import core.basesyntax.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionProcessor {
    void process(List<FruitTransaction> fruitTransactions);
}
