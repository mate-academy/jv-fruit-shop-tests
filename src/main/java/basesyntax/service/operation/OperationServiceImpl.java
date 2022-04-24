package basesyntax.service.operation;

import basesyntax.model.FruitTransaction;
import basesyntax.service.strategy.OperationStrategy;
import basesyntax.service.strategy.handlers.OperationHandler;
import java.util.List;

public class OperationServiceImpl implements OperationService {
    private final OperationStrategy operationStrategy;

    public OperationServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        if (fruitTransactions == null) {
            throw new NullPointerException("Input is null");
        }
        for (FruitTransaction fruit : fruitTransactions) {
            OperationHandler operationHandler = operationStrategy.get(fruit.getOperation());
            operationHandler.doOperation(fruit);
        }
    }
}
