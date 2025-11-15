package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int currentQuantity = Storage.getQuantity(transaction.getFruit());
        if (currentQuantity >= transaction.getQuantity()) {
            Storage.add(transaction.getFruit(), currentQuantity - transaction.getQuantity());
        } else {
            throw new RuntimeException("There is not enough of this fruit in the store.");
        }
    }
}
