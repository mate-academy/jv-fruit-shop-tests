package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.ActivityStrategy;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final ActivityStrategy activityStrategy;

    public FruitServiceImpl(ActivityStrategy activityStrategy) {
        this.activityStrategy = activityStrategy;
    }

    @Override
    public void processTransaction(List<FruitTransaction> transactionList) {
        for (FruitTransaction transaction : transactionList) {
            activityStrategy.getHandler(transaction.getActivityType()).handle(transaction);
        }
    }
}
