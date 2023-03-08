package core.basesyntax.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnHandler implements OperationHandler {
    @Override
    public void handler(FruitTransaction transaction) {
        if (Storage.storage.get(transaction.getFruit()) == 0) {
            throw new RuntimeException("We don`t know how many fruits are in storage");
        }
        Storage.storage.put(transaction.getFruit(),
                Storage.storage.get(transaction.getFruit()) + transaction.getQuantity());
    }
}
