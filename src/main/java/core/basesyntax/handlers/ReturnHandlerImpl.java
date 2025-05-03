package core.basesyntax.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class ReturnHandlerImpl implements OperationHandler {
    @Override
    public void handle(FruitTransaction fruitTransaction) {
        String fruitName = fruitTransaction.getFruitName();
        Integer storageFruitQuanity = Storage.fruitsStorage.get(fruitName);
        int transactionQuantity = fruitTransaction.getQuantity();
        Storage.fruitsStorage.replace(fruitName, storageFruitQuanity + transactionQuantity);
    }
}
