package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, FruitHandler> operationFruitHandlerMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation,
            FruitHandler> operationFruitHandlerMap) {
        this.operationFruitHandlerMap = operationFruitHandlerMap;
    }

    @Override
    public FruitHandler get(FruitTransaction.Operation operation) {
        return operationFruitHandlerMap.get(operation);
    }
}
