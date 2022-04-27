package core.basesyntax.service;

import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionServiceImpl implements TransactionService {
    private final OperationStrategy operationStrategy;

    public TransactionServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public Map<String, Integer> countsFruitsAfterWorkDay(List<FruitTransaction>
                                                                     fruitTransactions) {
        for (FruitTransaction fruit : fruitTransactions) {
            operationStrategy.getOperationHandler(fruit.getOperation())
                    .applyNewAmount(fruit.getFruitName(),
                            fruit.getQuantity());
        }
        return new HashMap<>(Storage.fruitTransactionStorage);
    }
}
