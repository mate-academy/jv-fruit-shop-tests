package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    Map<String, Integer> countsFruitsAfterWorkDay(List<FruitTransaction> fruitTransactions);
}
