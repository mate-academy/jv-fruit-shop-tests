package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessData;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class ProcessDataImpl implements ProcessData {
    private static OperationStrategy operationStrategy;

    public ProcessDataImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void operation(List<FruitTransaction> fruitTransactions) {
        fruitTransactions.forEach(
                fruitTransaction -> operationStrategy.getOperation(fruitTransaction.getOperation())
                        .operation(fruitTransaction)
        );
    }
}
