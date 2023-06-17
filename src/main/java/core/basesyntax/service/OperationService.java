package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class OperationService {
    private final OperationStrategy strategy;

    public OperationService(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    public void toFormStorage(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            strategy.get(transaction.getOperation()).handle(transaction);
        }
    }
}
