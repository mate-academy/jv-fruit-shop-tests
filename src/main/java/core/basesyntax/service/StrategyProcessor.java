package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import java.util.List;

public interface StrategyProcessor {
    void processTransactionStrategies(List<FruitTransaction> transactionList);
}
