package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface FruitHandler {
    void handleOperation(FruitTransaction transaction);
}
