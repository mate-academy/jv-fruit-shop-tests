package core.basesyntax.shop.handler;

import core.basesyntax.shop.model.FruitTransaction;

public interface OperationHandler {
    void operation(FruitTransaction transaction);
}
