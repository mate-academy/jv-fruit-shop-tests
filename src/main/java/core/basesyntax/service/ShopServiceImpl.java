package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.handler.OperationHandler;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy strategy;
    private final Storage storage;

    public ShopServiceImpl(OperationStrategy operationStrategy, Storage storage) {
        this.strategy = operationStrategy;
        this.storage = storage;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (storage == null || strategy == null || transactions == null) {
            throw new RuntimeException("input data can not be null");
        }

        if (transactions.isEmpty()) {
            throw new RuntimeException("transactions list is empty");
        }

        for (FruitTransaction transaction : transactions) {
            OperationHandler operationHandler
                    = strategy.operationHandler(transaction.getOperation());
            operationHandler.handle(transaction, storage);
        }
    }
}
