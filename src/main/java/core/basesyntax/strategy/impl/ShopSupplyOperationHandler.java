package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.ShopOperationHandler;

public class ShopSupplyOperationHandler implements ShopOperationHandler {
    @Override
    public void handle(Storage storage, String product, Integer quantity) {
        ShopHandlerValidator.validate(storage, product, quantity);
        storage.update(product, storage.getByKey(product) + quantity);
    }
}
