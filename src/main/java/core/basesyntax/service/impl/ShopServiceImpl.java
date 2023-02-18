package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.ShopService;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private core.basesyntax.strategy.OperationStrategy operationStrategy;

    public ShopServiceImpl(core.basesyntax.strategy.OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.handle(transaction);
        }
    }
}
