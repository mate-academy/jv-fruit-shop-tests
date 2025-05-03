package core.basesyntax.shop.strategy;

import core.basesyntax.shop.impl.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction fruitTransaction);
}
