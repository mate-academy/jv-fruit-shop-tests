package core.basesyntax.service.strategy;

import core.basesyntax.service.impl.DataProcessorImpl;

public interface OperationStrategy {
    OperationHandler get(DataProcessorImpl.OperationType operationType);
}
