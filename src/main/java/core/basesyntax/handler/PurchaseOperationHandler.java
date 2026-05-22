package core.basesyntax.handler;

import core.basesyntax.FruitTransaction;
import core.basesyntax.db.Storage;

public class PurchaseOperationHandler implements OperationHandler {
    private final Storage storage;

    public PurchaseOperationHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        int currentQuantity = storage.getFruits().getOrDefault(transaction.getFruit(), 0);
        if (currentQuantity < transaction.getQuantity()) {
            throw new RuntimeException("Can't purchase more than available");
        }
        storage.getFruits().put(transaction.getFruit(),
                currentQuantity - transaction.getQuantity());
    }
}
