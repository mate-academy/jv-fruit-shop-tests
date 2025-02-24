package core.basesyntax.strategy;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.transactor.Operation;
import java.util.Map;

public interface Strategy {
    OperationHandler get(Operation operation, Map<Operation, OperationHandler> map);
}
