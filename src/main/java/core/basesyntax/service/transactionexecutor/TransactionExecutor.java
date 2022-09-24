package core.basesyntax.service.transactionexecutor;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionExecutor {
    void executeTransaction(List<FruitTransaction> fruitList);
}
