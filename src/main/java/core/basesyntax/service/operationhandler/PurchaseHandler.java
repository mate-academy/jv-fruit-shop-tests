package core.basesyntax.service.operationhandler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handleOperation(FruitTransaction transaction) {
        if (transaction == null) {
            throw new NullPointerException("Transaction cannot be null");
        }
        if (transaction.getFruit() == null || transaction.getFruit().isBlank()) {
            throw new IllegalArgumentException("Fruit name cannot be null or empty");
        }
        if (transaction.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Integer currentQuantity = Storage.getFruits().get(transaction.getFruit());

        if (currentQuantity == null || currentQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Not enough in stock to sell "
                    + transaction.getQuantity() + " of " + transaction.getFruit());
        }

        Storage.getFruits().put(transaction.getFruit(),
                currentQuantity - transaction.getQuantity());
    }
}
