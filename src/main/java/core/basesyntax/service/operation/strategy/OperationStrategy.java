package core.basesyntax.service.operation.strategy;

import core.basesyntax.service.operation.handler.OperationTypeHandler;
import core.basesyntax.service.operation.handler.Operations;

public interface OperationStrategy {
    OperationTypeHandler get(Operations operation);
}
