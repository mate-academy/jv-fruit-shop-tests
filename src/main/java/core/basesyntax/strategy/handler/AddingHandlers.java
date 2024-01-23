package core.basesyntax.strategy.handler;

import core.basesyntax.dao.WorkWithStorageDB;
import core.basesyntax.dao.WorkWithStorageImpl;
import core.basesyntax.model.FruitTransaction;

public class AddingHandlers implements Handler {
    private WorkWithStorageDB storageService = new WorkWithStorageImpl();

    @Override
    public void handler(FruitTransaction fruitTransaction) {
        if (fruitTransaction.getQuantity() <= 0) {
            throw new RuntimeException("Can't work with this quantity "
                        + fruitTransaction.getQuantity());
        }
        if (storageService
                .getFromStorage(fruitTransaction.getFruit()) == null) {
            storageService
                    .addInStorage(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity());
        } else {
            Integer oldQuantity = storageService.getFromStorage(fruitTransaction.getFruit());
            storageService
                    .addInStorage(fruitTransaction.getFruit(),
                            fruitTransaction.getQuantity() + oldQuantity);
        }
    }
}
