package core.basesyntax.store.service.impl;

import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.service.ShopService;
import core.basesyntax.store.strategy.OperationHandler;
import core.basesyntax.store.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.apply(transaction);
        }
    }
}
