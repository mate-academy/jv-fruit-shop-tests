package core.basesyntax.shop.service;

import core.basesyntax.shop.service.operations.OperationHandler;

public interface OperationsStrategy {
    OperationHandler get(String operation);
}
