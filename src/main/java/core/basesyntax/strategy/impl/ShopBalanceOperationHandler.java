package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.ShopOperationHandler;

public class ShopBalanceOperationHandler implements ShopOperationHandler {
    @Override
    public void handle(Storage storage, String product, Integer quantity) {
        ShopHandlerValidator.validate(storage, product, quantity);
        storage.update(product, quantity);
    }
}
