package core.basesyntax.shop.service.impl;

import core.basesyntax.shop.handler.strategy.OperationStrategy;
import core.basesyntax.shop.model.FruitTransaction;
import core.basesyntax.shop.service.CalculateQuantityService;
import java.util.List;

public class CalculateQuantityServiceImpl implements CalculateQuantityService {
    private final OperationStrategy operationStrategy;

    public CalculateQuantityServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void calculate(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operationStrategy.getOperationHandler(fruitTransaction.getOperationType())
                    .operation(fruitTransaction);
        }
    }
}
