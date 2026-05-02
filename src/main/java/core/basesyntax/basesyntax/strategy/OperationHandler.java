package core.basesyntax.basesyntax.strategy;

import core.basesyntax.basesyntax.db.StorageImpl;

public interface OperationHandler {
    void apply(String fruit, int quantity, StorageImpl storage);
}
