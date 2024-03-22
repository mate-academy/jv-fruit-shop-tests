package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation;

import java.util.Map;
import java.util.Objects;

public class OperationStrategy {
    private final Map<Operation, OperationHandler> strategyMap;

    public OperationStrategy(Map<Operation, OperationHandler> strategyMap) {
        Objects.requireNonNull(strategyMap);
        this.strategyMap = strategyMap;
    }

    public OperationHandler getHandlerFor(Operation operation) {
        Objects.requireNonNull(operation);
        return strategyMap.get(operation);
    }
}
