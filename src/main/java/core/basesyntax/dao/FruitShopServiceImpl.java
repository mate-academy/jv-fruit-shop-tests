package core.basesyntax.dao;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.StoreOperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.List;

public class FruitShopServiceImpl implements FruitShopService {
    private StoreOperationStrategy operationStrategy;

    public FruitShopServiceImpl(StoreOperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void addDataToStorage(List<FruitTransaction> dataList) {
        for (FruitTransaction transaction : dataList) {
            OperationHandler operation = operationStrategy
                    .getOperation(transaction.getStoreOperation());
            operation.handle(transaction);
        }
    }
}
