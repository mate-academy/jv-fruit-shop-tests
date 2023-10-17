package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandlerImpl implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        String fruitName = transaction.getFruit();
        int transactionQuantity = transaction.getQuantity();
        if (Storage.getStorage().containsKey(fruitName)) {
            int updatedQuantity = Storage.getStorage().get(fruitName) - transactionQuantity;
            if (updatedQuantity < 0) {
                throw new RuntimeException("We don't have enough fruits!");
            }
            Storage.getStorage().replace(fruitName, updatedQuantity);
        }
    }
}
