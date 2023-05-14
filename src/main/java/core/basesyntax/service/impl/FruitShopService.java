package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.FactoryStrategy;
import java.util.List;

public class FruitShopService {
    private final FactoryStrategy factoryStrategy;

    public FruitShopService(FactoryStrategy factoryStrategy) {
        this.factoryStrategy = factoryStrategy;
    }

    public void processing(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationStrategy operationStrategy = factoryStrategy
                    .getOperationStrategy(transaction.getOperation());
            operationStrategy.executeOperation(transaction.getFruit(), transaction.getAmount());
        }
    }
}
