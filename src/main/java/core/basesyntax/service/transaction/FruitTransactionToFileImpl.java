package core.basesyntax.service.transaction;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationStrategy;

import java.util.List;

public class FruitTransactionToFileImpl implements FruitTransactionToFile {
    private final OperationStrategy operationStrategy;

    public FruitTransactionToFileImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processTransaction(List<FruitTransaction> fruitTransactionList) {
        fruitTransactionList.forEach(fruitTransaction -> operationStrategy
            .getOperationHandler(fruitTransaction.getOperation())
            .operation(fruitTransaction));
    }
}
