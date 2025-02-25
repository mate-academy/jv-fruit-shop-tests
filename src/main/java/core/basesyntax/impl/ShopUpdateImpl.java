package core.basesyntax.impl;

import core.basesyntax.service.ShopUpdateService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.FruitTransaction;
import java.util.List;

public class ShopUpdateImpl implements
        ShopUpdateService {
    private final Strategy operationStrategy;

    public ShopUpdateImpl(Strategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void update(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction: fruitTransactions) {
            operationStrategy.getOperationHandler(fruitTransaction.getOperation())
                    .operate(fruitTransaction);
        }
    }
}
