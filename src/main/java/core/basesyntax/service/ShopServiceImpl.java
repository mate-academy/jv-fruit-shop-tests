package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;
    private Map<String, Integer> storage = new HashMap<>();

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    public ShopServiceImpl(OperationStrategy operationStrategy,
                           Map<String, Integer> storage) {
        this.storage = storage;
        this.operationStrategy = operationStrategy;
    }

    public Map<String, Integer> getStorage() {
        return storage;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        if (transactions == null) {
            throw new RuntimeException("Transactions is null");
        }

        if (transactions.isEmpty()) {
            throw new RuntimeException("Transactions is empty");
        }

        for (FruitTransaction transaction : transactions) {
            OperationHandler operationHandler = operationStrategy
                    .getOperationHandler(transaction.getOperation());

            operationHandler.handleOperation(transaction, storage);
        }
    }
}
