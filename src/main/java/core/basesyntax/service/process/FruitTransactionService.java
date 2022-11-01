package core.basesyntax.service.process;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionService {
    void processTransactions(List<FruitTransaction> fruitTransactions);
}
