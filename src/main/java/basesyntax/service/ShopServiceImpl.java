package basesyntax.service;

import basesyntax.model.FruitTransaction;
import basesyntax.service.handler.OperationHandler;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactionList) {
        for (FruitTransaction transaction : fruitTransactionList) {
            OperationHandler handler = operationStrategy.getHandler(transaction.getOperation());
            handler.handle(transaction);
        }
    }
}
