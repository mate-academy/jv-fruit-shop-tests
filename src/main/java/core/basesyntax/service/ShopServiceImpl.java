package core.basesyntax.service;

import core.basesyntax.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.transaction.FruitTransaction;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = operationStrategy.get(transaction.getOperation());
            if (handler == null) {
                throw new RuntimeException("Handler not found for operation: "
                        + transaction.getOperation());
            }
            handler.apply(transaction);
        }
    }
}
