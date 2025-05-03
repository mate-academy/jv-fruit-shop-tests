package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperatorStrategy;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private OperatorStrategy operationStrategy;

    public FruitShopServiceImpl(OperatorStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler operationHandler = operationStrategy.getHandler(transaction);
            operationHandler.operateFruits(transaction);
        }
    }
}
