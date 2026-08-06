package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null || transactions.isEmpty()) {
            return;
        }

        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy
                    .getHandler(transaction.getOperation());

            if (handler != null) {
                handler.operate(Storage.getFruits(), transaction);
            }
        }
    }
}
