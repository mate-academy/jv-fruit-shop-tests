package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.exception.ReturnOperationException;
import core.basesyntax.strategy.handler.OperationHandler;

public class ReturnHandler implements OperationHandler {

    @Override
    public void handle(ProductTransaction productTransaction) {
        String product = productTransaction.product();
        int quantity = productTransaction.quantity();
        if (!ProductStorage.STORAGE.containsKey(product)) {
            throw new ReturnOperationException("Can't return absent product: " + product);
        }
        if (quantity <= 0) {
            throw new ReturnOperationException("Quantity must be a positive number " + quantity);
        }
        Integer currentBalance = ProductStorage.STORAGE.get(product);
        ProductStorage.STORAGE.put(product, currentBalance + quantity);
    }
}
