package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.CalculationStrategy;
import core.basesyntax.service.OperationHandler;
import java.util.Map;

public class CalculationStrategyImpl implements CalculationStrategy {
    private Map<Operation, OperationHandler> calculationHandlerMap;

    public CalculationStrategyImpl(Map<Operation, OperationHandler> calculationHandlerMap) {
        if (calculationHandlerMap == null) {
            throw new IllegalArgumentException("Calculation handler map can't be null");
        }
        this.calculationHandlerMap = calculationHandlerMap;
    }

    @Override
    public OperationHandler get(Operation operation) {
        return calculationHandlerMap.get(operation);
    }
}
