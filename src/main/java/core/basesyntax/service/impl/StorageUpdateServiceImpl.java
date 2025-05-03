package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.StorageUpdateService;
import core.basesyntax.service.amount.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.List;

public class StorageUpdateServiceImpl implements StorageUpdateService {
    private final OperationStrategy strategy;

    public StorageUpdateServiceImpl() {
        this.strategy = new OperationStrategyImpl();
    }

    @Override
    public void update(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction transaction : fruitTransactions) {
            OperationHandler operation = strategy.getOperationHandler(transaction.getOperation());
            operation.process(transaction);
        }
    }
}
