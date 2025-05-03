package core.basesyntax.services.operation;

import core.basesyntax.services.transaction.model.ProductTransaction;

public interface OperationHandler {
    void handle(ProductTransaction transaction);
}
