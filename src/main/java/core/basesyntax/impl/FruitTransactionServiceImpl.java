package core.basesyntax.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.Strategy;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitService {
    private final Strategy operationStrategy;

    public FruitTransactionServiceImpl(Strategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new NullPointerException("Fruit transaction cannot be null");
        }
        fruitTransactions.forEach(f -> operationStrategy.get(f.getOperation()).handle(f));
    }
}
