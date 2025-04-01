package core.basesyntax.strategy.implementation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class PurchaseHandlerImpl implements OperationHandler {

    @Override
    public void processTransaction(FruitTransaction transaction) {
        Integer oldQuantity = Storage.getStorage().get(transaction.getFruit());
        if (oldQuantity - transaction.getQuantity() < 0) {
            throw new RuntimeException("Not enough " + transaction.getFruit() + " in stock");
        }
        Storage.getStorage().put(transaction.getFruit(), oldQuantity - transaction.getQuantity());
    }
}
