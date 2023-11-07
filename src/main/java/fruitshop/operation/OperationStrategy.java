package fruitshop.operation;

import fruitshop.model.Operation;

public interface OperationStrategy {
    OperationHandler get(Operation operationType);
}
