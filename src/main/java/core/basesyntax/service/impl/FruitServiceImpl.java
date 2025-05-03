package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.interfaces.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;
import java.util.Map;

public class FruitServiceImpl implements FruitService {
    private final Map<String, OperationStrategy> operationStrategies;

    public FruitServiceImpl(Map<String, OperationStrategy> operationStrategies) {
        this.operationStrategies = operationStrategies;
    }

    @Override
    public void processTransactions(List<FruitTransaction> transactions) {
        for (FruitTransaction fruitTransaction : transactions) {
            FruitTransaction.Operation operationType = fruitTransaction.getOperation();
            if (operationType == null) {
                throw new IllegalArgumentException("Operation type cannot be null");
            }
            OperationStrategy strategy = operationStrategies.get(operationType.getCode());
            if (strategy == null) {
                throw new IllegalArgumentException(
                        "No strategy found for operation: " + operationType.getCode());
            }
            strategy.applyStrategy(fruitTransaction);
        }
    }
}
