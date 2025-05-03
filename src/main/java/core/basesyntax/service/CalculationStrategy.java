package core.basesyntax.service;

import core.basesyntax.model.Operation;

public interface CalculationStrategy {
    OperationHandler get(Operation operation);
}
