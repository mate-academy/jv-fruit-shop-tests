package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationStrategy implements OperationStrategy {
    @Override
    public void applyStrategy(FruitTransaction transaction) {
        String fruitName = transaction.getFruit();
        int quantity = transaction.getQuantity();
        if (quantity < 0) {
            throw new IllegalArgumentException("Negative fruit quantity not allowed: "
                    + transaction.getFruit());
        }
        Storage storage = Storage.getInstance();
        storage.updateFruitQuantity(FruitTransaction
                .Operation.PURCHASE, fruitName, quantity);
    }
}
