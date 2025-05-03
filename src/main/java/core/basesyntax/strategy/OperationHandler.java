package core.basesyntax.strategy;

import core.basesyntax.dto.ShopOperation;

public interface OperationHandler {
    int apply(ShopOperation shopOperation);
}
