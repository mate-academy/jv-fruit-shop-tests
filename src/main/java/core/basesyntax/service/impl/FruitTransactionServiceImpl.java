package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private OperationStrategy operationStrategy;

    public FruitTransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void transfer(List<FruitTransaction> values) {
        if (values == null) {
            throw new RuntimeException("Invalid data");
        }
        values.forEach(e -> operationStrategy.get(e.getOperation())
                .apply(e.getFruit(), e.getQuantity()));
    }
}
