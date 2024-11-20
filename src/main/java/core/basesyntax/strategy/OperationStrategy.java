package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlers;

    public OperationStrategy(Map<FruitTransaction.Operation, OperationHandler> operationHandlers) {
        this.operationHandlers = operationHandlers;
    }

    public void execute(FruitTransaction.Operation operation,
                        FruitTransaction transaction,
                        Map<String, Integer> inventory) {
        operationHandlers.get(operation).handle(transaction, inventory);
    }
}
