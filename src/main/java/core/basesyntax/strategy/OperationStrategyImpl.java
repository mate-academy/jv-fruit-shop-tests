package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import java.util.Optional;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> handlersMap;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlersMap) {
        this.handlersMap = handlersMap;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation type) {
        return Optional.ofNullable(handlersMap.get(type))
                .orElseThrow(() -> new NullPointerException("No handler found for type: " + type));
    }
}
