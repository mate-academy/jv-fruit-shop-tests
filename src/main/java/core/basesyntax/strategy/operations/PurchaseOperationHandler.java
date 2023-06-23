package core.basesyntax.strategy.operations;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        checkTransaction(transaction);
        int currentQuantity = Storage.getStorage().get(transaction.getFruit());
        if (currentQuantity < transaction.getQuantity()) {
            throw new RuntimeException("You can buy only "
                    + currentQuantity + " " + transaction.getFruit());
        }
        Storage.getStorage()
                .put(transaction.getFruit(), currentQuantity - transaction.getQuantity());
    }
}
