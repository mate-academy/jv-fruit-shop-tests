package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface TransactionProcessor {
    Map<String, Integer> process(List<FruitTransaction> fruitTransactions);
}
