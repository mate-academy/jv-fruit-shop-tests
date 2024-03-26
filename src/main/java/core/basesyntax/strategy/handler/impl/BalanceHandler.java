package core.basesyntax.strategy.handler.impl;

import core.basesyntax.db.ProductStorage;
import core.basesyntax.dto.ProductTransaction;
import core.basesyntax.exception.BalanceOperationException;
import core.basesyntax.strategy.handler.OperationHandler;

public class BalanceHandler implements OperationHandler {

    @Override
    public void handle(ProductTransaction productTransaction) {
        String product = productTransaction.product();
        int quantity = productTransaction.quantity();
        if (quantity <= 0) {
            throw new BalanceOperationException("Quantity must be a positive number ");
        }
        if (ProductStorage.STORAGE.containsKey(product)) {
            throw new BalanceOperationException("Balance can't be reassigned! "
                    + "You have duplicate balance operation for product: " + product);
        }
        ProductStorage.STORAGE.putIfAbsent(product, quantity);
    }
}
