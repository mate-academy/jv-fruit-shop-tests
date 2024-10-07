package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;

import java.util.List;
import java.util.Map;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;
    private final Map<String, Integer> storage;

    public ShopServiceImpl(OperationStrategy operationStrategy, Map<String, Integer> storage) {
        this.operationStrategy = operationStrategy;
        this.storage = storage;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy.get(transaction.getOperation());
            handler.apply(transaction.getFruit(), transaction.getQuantity(), storage);
        }
    }
}
