package core.basesyntax.store.strategy;

import core.basesyntax.store.model.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction fruitTransaction);
}
