package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processOfOperations(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null || fruitTransactions.isEmpty()) { //added after approval
            throw new RuntimeException("List of data transactions is null or empty");
        }
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation()).handle(fruitTransaction);
        }
    }
}
