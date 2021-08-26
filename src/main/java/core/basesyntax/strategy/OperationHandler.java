package core.basesyntax.strategy;

import core.basesyntax.dto.ShopOperation;

public interface OperationHandler {
    void apply(ShopOperation shopOperation);
}
