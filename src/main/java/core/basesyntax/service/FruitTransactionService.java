package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionService {
    String processTransactions(List<FruitTransaction> transactions);
}
