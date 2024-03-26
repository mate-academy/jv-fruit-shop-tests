package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.exception.PurchaseOperationException;
import core.basesyntax.strategy.handler.OperationHandler;

public class PurchaseHandler implements OperationHandler {

    @Override
    public void handle(ProductTransaction productTransaction) {
        int quantity = productTransaction.quantity();
        String product = productTransaction.product();
        Integer currentBalance = ProductStorage.STORAGE.get(product);
        if (quantity <= 0) {
            throw new PurchaseOperationException("Quantity must be a positive number " + quantity);
        }
        if (currentBalance < quantity) {
            throw new PurchaseOperationException(
                    "Unable to purchase product: " + product
                            + " balance is less than purchase quantity."
                            + System.lineSeparator()
                            + "Current balance: " + currentBalance
                            + System.lineSeparator()
                            + "Purchase quantity: " + quantity
            );
        }
        ProductStorage.STORAGE.put(product, currentBalance - quantity);
    }
}
