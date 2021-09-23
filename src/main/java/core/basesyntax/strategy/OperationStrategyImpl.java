package core.basesyntax.strategy;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.operation.Calculator;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private Map<Fruit.OperationType, Calculator> operationHandlerMap;

    public OperationStrategyImpl(Map<Fruit.OperationType, Calculator> operationHandlerMap) {
        this.operationHandlerMap = operationHandlerMap;
    }

    @Override
    public Calculator get(Fruit.OperationType type) {
        return operationHandlerMap.get(type);
    }
}
