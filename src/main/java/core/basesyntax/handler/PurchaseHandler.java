package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (Storage.storage.get(transaction.getFruit()) < transaction.getQuantity()
                || Storage.storage.get(transaction.getFruit()) == 0) {
            throw new RuntimeException("We don`t have enough fruits in storage");
        }
        Storage.storage.put(transaction.getFruit(),
                Storage.storage.get(transaction.getFruit()) - transaction.getQuantity());
    }
}
