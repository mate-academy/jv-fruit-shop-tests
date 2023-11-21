package core.basesyntax.service;

import java.util.List;
import core.basesyntax.model.FruitTransaction;

public interface FruitTransactionService {
    String processTransactions(List<FruitTransaction> transactions);
}
