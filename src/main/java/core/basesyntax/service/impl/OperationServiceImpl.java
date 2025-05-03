package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandlerStrategy;
import core.basesyntax.service.OperationService;
import java.util.List;

public class OperationServiceImpl implements OperationService {
    private final OperationHandlerStrategy operationHandlerStrategy;

    public OperationServiceImpl(OperationHandlerStrategy operationHandlerStrategy) {
        this.operationHandlerStrategy = operationHandlerStrategy;
    }

    @Override
    public void calculate(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions.isEmpty()) {
            throw new RuntimeException("Empty input");
        }
        fruitTransactions.forEach(i -> operationHandlerStrategy
                .get(i.getOperation()).apply(i));
    }
}
