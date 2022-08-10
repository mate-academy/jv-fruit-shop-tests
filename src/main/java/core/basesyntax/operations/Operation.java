package core.basesyntax.operations;

import core.basesyntax.FruitTransaction;
import core.basesyntax.storage.Storage;

public interface Operation {
    void performOperation(Storage storage, FruitTransaction fruitTransaction);
}
