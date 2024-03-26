package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation;

import core.basesyntax.exception.IllegalInputDataException;
import java.util.Map;

public class OperationStrategy {
    private final Map<Operation, OperationHandler> strategyMap;

    public OperationStrategy(Map<Operation, OperationHandler> strategyMap) {
        if (strategyMap == null) {
            throw new IllegalInputDataException("Strategy map can`t be null");
        }

        this.strategyMap = strategyMap;
    }

    public OperationHandler getHandlerFor(Operation operation) {
        if (operation == null) {
            throw new IllegalInputDataException("Operation can`t be null");
        }

        return strategyMap.get(operation);
    }
}
