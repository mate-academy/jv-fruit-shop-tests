package core.basesyntax.transactionexecutor;

import core.basesyntax.fruittransaction.FruitTransaction;
import java.util.List;

public interface TransactionExecutor {
    void execute(List<FruitTransaction> transactions);
}
