package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            throw new RuntimeException("Transactions can't be null");
        }
        for (FruitTransaction transaction : transactions) {
            if (transaction == null) {
                throw new RuntimeException("Transactions can't be null...");
            }
            OperationHandler handler = operationStrategy.get(transaction.getOperation());
            handler.apply(transaction);
        }
    }
}
