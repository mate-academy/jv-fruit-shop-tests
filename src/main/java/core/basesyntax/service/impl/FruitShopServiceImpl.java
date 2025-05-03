package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.FruitShopService;
import core.basesyntax.service.strategy.OperationStrategy;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private final OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processData(List<FruitTransaction> transactionList) {
        for (FruitTransaction fruitTransaction : transactionList) {
            operationStrategy.processOperation(fruitTransaction.getOperationType())
                    .applyOperation(fruitTransaction);
        }
    }
}
