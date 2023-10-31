package core.basesyntax.strategy;

import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperationStrategy(Map<FruitTransaction.Operation, OperationHandler>
                                     operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new FruitShopException("Operation can't be null!");
        }
        return operationHandlerMap.get(operation);
    }
}
