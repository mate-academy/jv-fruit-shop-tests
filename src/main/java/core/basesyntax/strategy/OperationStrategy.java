package core.basesyntax.strategy;

import core.basesyntax.model.ShopTransaction;

public interface OperationStrategy {
    ShopOperationHandler resolve(ShopTransaction.Operation operationType);
}
