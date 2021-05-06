package core.basesyntax.service;

import core.basesyntax.service.operation.Operation;

public interface OperationStrategy {
    Operation get(String activity);
}
