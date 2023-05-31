package core.basesyntax.strategy.impl;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> handlerOperationMap;

    public OperationStrategyImpl(
            Map<FruitTransaction.Operation, OperationHandler> handlerOperationMap) {
        this.handlerOperationMap = handlerOperationMap;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction.Operation operation) {
        return handlerOperationMap.get(operation);
    }
}
