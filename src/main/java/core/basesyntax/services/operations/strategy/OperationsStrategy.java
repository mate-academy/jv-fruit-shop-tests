package core.basesyntax.services.operations.strategy;

import core.basesyntax.services.operations.Operation;

public interface OperationsStrategy {
    Operation getOperation(String operation);
}
