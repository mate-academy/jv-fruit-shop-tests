package core.basesyntax.strategy.handlers;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction transaction);

    static void check(String fruit) {
        if (!FruitStorage.storage.containsKey(fruit)) {
            throw new RuntimeException("Database does not contain: " + fruit);
        }
    }
}
