package core.basesyntax.strategy.activities;

import core.basesyntax.model.FruitTransaction;

public interface ActivitiesHandler {
    void operation(FruitTransaction fruitTransaction);
}
