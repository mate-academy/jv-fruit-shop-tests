package core.basesyntax.strategy.handler;

import core.basesyntax.dto.ProductTransaction;

public interface OperationHandler {
    void handle(ProductTransaction productTransaction);
}
