package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public interface FruitHandler {
    Storage storage = new Storage();
    void apply(FruitTransaction fruitTransaction);
}
