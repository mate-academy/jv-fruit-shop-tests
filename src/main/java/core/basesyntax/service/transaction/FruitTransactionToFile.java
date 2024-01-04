package core.basesyntax.service.transaction;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface FruitTransactionToFile {
    void processTransaction(List<FruitTransaction> fruitTransactionList);
}
