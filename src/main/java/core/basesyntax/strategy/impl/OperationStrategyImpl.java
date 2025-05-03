package core.basesyntax.strategy.impl;

import core.basesyntax.servise.impl.FruitTransaction;
import core.basesyntax.strategy.MapOfHandlersForStrategy;
import core.basesyntax.strategy.OperationService;
import core.basesyntax.strategy.OperationStrategy;

public class OperationStrategyImpl implements OperationStrategy {
    private final MapOfHandlersForStrategy mapForStrategy;

    public OperationStrategyImpl(MapOfHandlersForStrategy mapForStrategy) {
        if (mapForStrategy == null || mapForStrategy.getHandlers().isEmpty()) {
            throw new IllegalArgumentException(
                    "The Map of handlers for strategy is NULL or empty");
        }
        this.mapForStrategy = mapForStrategy;
    }

    @Override
    public OperationService getOperationHandler(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new IllegalArgumentException("The fruit transaction is NULL or empty");
        }
        return mapForStrategy.getHandlers().get(fruitTransaction.getOperation());
    }
}
