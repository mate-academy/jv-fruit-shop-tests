package core.basesyntax.basesyntax.strategy;

import core.basesyntax.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction transaction);
}
