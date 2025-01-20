package core.basesyntax.service;

import core.basesyntax.dao.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> transactions) {
        for (FruitTransaction transaction : transactions) {
            operationStrategy.getOperationHandler(transaction
                    .getOperation()).handle(Storage.storage, transaction);
        }

    }
}
