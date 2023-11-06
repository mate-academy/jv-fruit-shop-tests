package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction.OperationType;
import java.util.Map;

public class HandlerStrategy {
    private static final String EXCEPTION_MESSAGE = "Can't get handler by type";
    private static Map<OperationType, OperationHandler> strategyMap;

    public HandlerStrategy(Map<OperationType, OperationHandler> strategyMap) {
        HandlerStrategy.strategyMap = strategyMap;
    }

    public Map<OperationType, OperationHandler> getStrategyMap() {
        return strategyMap;
    }

    public OperationHandler getHandlerByOperationType(OperationType operationType) {
        if (operationType == null) {
            throw new RuntimeException(EXCEPTION_MESSAGE);
        }
        return strategyMap.get(operationType);
    }
}
