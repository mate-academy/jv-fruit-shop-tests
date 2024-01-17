package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.FruitOperationStrategy;
import java.util.List;
import java.util.Map;

public class FruitServiceImpl implements FruitService {
    private final Map<Operation, FruitOperationStrategy> strategyMap;

    public FruitServiceImpl(Map<Operation, FruitOperationStrategy> strategyMap) {
        this.strategyMap = strategyMap;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("List of transactions is null");
        }
        FruitOperationStrategy strategy;
        for (FruitTransaction transaction : transactions) {
            strategy = strategyMap.get(transaction.getOperation());
            strategy.apply(transaction);
        }
    }
}
