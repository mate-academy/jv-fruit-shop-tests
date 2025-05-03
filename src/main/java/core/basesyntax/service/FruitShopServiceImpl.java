package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private final OperationStrategy operationStrategy;

    public FruitShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void provideOperation(List<FruitTransaction> fruitTransaction) {
        for (FruitTransaction transaction : fruitTransaction) {
            OperationHandler operationHandler =
                    operationStrategy.getHandler(transaction.getOperation());
            operationHandler.handle(transaction);
        }
    }
}
