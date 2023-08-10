package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;
import java.util.Map;

public interface FruitTransactionService {
    Map<String, List<FruitTransaction>> readFruitTransaction();

    Map<String, Integer> calcFruitTransaction(Map<String, List<FruitTransaction>> transactionMap);
}
