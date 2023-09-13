package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperatorStrategy;
import java.util.Map;

public class OperatorStrategyImpl implements OperatorStrategy {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    public OperatorStrategyImpl(Map<FruitTransaction.Operation, OperationHandler>
                                        operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    public Map<FruitTransaction.Operation, OperationHandler> getOperationHandlerMap() {
        return operationHandlerMap;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction transaction) {
        return operationHandlerMap.get(transaction.getOperation());
    }
}
