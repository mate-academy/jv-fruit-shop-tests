package core.basesyntax.strategy.implementation;

import core.basesyntax.OperationType;
import core.basesyntax.strategy.interfaces.OperationHandler;
import core.basesyntax.strategy.interfaces.OperationStrategy;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<OperationType, OperationHandler> handlersMap;

    public OperationStrategyImpl(Map<OperationType, OperationHandler> handlersMap) {
        this.handlersMap = handlersMap;
    }

    @Override
    public OperationHandler getStrategy(OperationType type) {
        return handlersMap.get(type);
    }
}
