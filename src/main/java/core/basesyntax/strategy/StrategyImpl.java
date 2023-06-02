package core.basesyntax.strategy;

import core.basesyntax.exception.NullOperationException;
import core.basesyntax.models.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import java.util.Map;

public class StrategyImpl implements Strategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public StrategyImpl(Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation == null) {
            throw new NullOperationException("Cant handle FruitTransaction with null operation");
        }
        return operationHandlerMap.get(operation);
    }
}
