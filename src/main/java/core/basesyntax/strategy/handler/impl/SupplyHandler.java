package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.strategy.handler.OperationHandler;

public class SupplyHandler extends OperationHandler {

    @Override
    public void handle(ProductTransaction productTransaction) {
        String product = productTransaction.product();
        int quantity = productTransaction.quantity();
        if (ProductStorage.STORAGE.containsKey(product)) {
            ProductStorage.STORAGE.put(
                    product, ProductStorage.STORAGE.get(product) + quantity);
        }
        ProductStorage.STORAGE.putIfAbsent(product, quantity);
    }
}
