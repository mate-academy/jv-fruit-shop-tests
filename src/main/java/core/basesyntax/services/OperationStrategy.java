package core.basesyntax.services;

import core.basesyntax.services.operation.OperationHandler;
import core.basesyntax.services.transaction.model.ProductTransaction;

public interface OperationStrategy {
    OperationHandler get(ProductTransaction.Operation operation);
}
