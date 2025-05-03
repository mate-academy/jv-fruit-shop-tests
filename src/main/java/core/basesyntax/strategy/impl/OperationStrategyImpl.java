package core.basesyntax.strategy.impl;

import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private final Map<FruitTransaction.Operation, OperationHandler> strategy;

    public OperationStrategyImpl(Map<FruitTransaction.Operation, OperationHandler> strategy) {
        this.strategy = strategy;
    }

    @Override
    public OperationHandler get(FruitTransaction.Operation operation) {
        if (operation != null) {
            return strategy.get(operation);
        } else {
            throw new InvalidDataException("Operation is not exist");
        }
    }
}
