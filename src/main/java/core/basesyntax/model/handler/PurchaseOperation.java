package core.basesyntax.model.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentBalance = Storage.getStorage().getOrDefault(transaction.getFruit(), 0);
        int purchaseQuantity = transaction.getQuantity();

        if (purchaseQuantity <= currentBalance && purchaseQuantity > 0) {
            int updateQuantity = currentBalance - purchaseQuantity;
            Storage.getStorage().put(transaction.getFruit(), updateQuantity);
        } else {
            throw new IllegalArgumentException("Invalid purchase Quantity" + purchaseQuantity);
        }
    }
}
