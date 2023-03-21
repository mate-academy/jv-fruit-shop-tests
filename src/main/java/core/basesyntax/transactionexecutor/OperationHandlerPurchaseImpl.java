package core.basesyntax.transactionexecutor;

import core.basesyntax.db.Storage;
import core.basesyntax.fruittransaction.FruitTransaction;

public class OperationHandlerPurchaseImpl implements OperationHandler {

    @Override
    public void handle(FruitTransaction transaction) {
        Integer quantityFruitsFromStorage = Storage.getStorage().get(transaction.getFruit());
        Storage.put(transaction.getFruit(),
                quantityFruitsFromStorage - transaction.getQuantity());
    }
}

