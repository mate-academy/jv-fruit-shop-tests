package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.ShopOperationHandler;

public class ShopPurchaseOperationHandler implements ShopOperationHandler {
    @Override
    public void handle(Storage storage, String product, Integer quantity) {
        ShopHandlerValidator.validate(storage, product, quantity);
        int stockQuantity = storage.getByKey(product);

        if (stockQuantity < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product);
        }

        storage.update(product, stockQuantity - quantity);
    }
}
