package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface TransactionHandler {
    void handleTransactions(List<FruitTransaction> transactionList);
}
