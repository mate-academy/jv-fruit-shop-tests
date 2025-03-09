package core.basesyntax.impl;

import core.basesyntax.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ShopServiceImpl implements ShopService {
    private final OperationStrategy operationStrategy;

    public ShopServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        fruitTransactions.forEach(fruitTransaction ->
                operationStrategy.getOperationHandler(
                        FruitTransaction.Operation.valueOf(fruitTransaction.getOperation()))
                        .apply(fruitTransaction));
    }
}
