package core.basesyntax.strategy;

import core.basesyntax.handlers.OperationTypeHandler;
import java.util.Map;
import core.basesyntax.model.FruitTransaction;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationTypeHandler> strategy;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationTypeHandler> strategy) {
        this.strategy = strategy;
    }

    @Override
    public OperationTypeHandler getHandlerByOperation(FruitTransaction.Operation type) {
        return strategy.get(type);
    }
}
