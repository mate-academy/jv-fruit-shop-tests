package core.basesyntax.strategy;

import core.basesyntax.db.Storage;

public interface ShopOperationHandler {
    void handle(Storage storage, String product, Integer quantity);
}
