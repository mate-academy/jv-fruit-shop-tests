package core.basesyntax.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.service.ShopUpdateService;
import core.basesyntax.strategy.Strategy;
import core.basesyntax.transactor.FruitTransaction;
import core.basesyntax.transactor.Operation;
import java.util.List;
import java.util.Map;

public class ShopUpdateImpl implements
        ShopUpdateService<FruitTransaction> {
    private final Strategy operationStrategy;

    public ShopUpdateImpl(Strategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void update(List<FruitTransaction> fruitTransactions,
                       Map<Operation, OperationHandler> map) {
        for (FruitTransaction fruitTransaction: fruitTransactions) {
            operationStrategy.get(fruitTransaction.getOperation(), map)
                    .operate(fruitTransaction);
        }
    }
}
