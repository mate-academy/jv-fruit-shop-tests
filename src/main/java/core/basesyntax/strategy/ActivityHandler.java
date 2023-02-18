package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface ActivityHandler {
    void handle(FruitTransaction transaction);
}
