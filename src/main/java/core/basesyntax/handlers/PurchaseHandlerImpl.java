package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseHandlerImpl implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        Integer storageFruitQuanity = Storage.fruitsStorage.get(fruitName);
        int transactionQuantity = fruitTransaction.getQuantity();
        if (storageFruitQuanity - transactionQuantity < 0) {
            throw new RuntimeException("No enough :" + fruitName);
        }
        Storage.fruitsStorage.put(fruitName, storageFruitQuanity - transactionQuantity);
    }
}
