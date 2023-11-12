package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handlers.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> handlers;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public OperationHandler getHandler(FruitTransaction fruitTransaction) {
        if (fruitTransaction == null) {
            throw new NullPointerException("Fruit transaction is null");
        }
        if (handlers.isEmpty()) {
            throw new RuntimeException("Handlers map is empty");
        }
        return handlers.get(fruitTransaction.getOperation());
    }
}
