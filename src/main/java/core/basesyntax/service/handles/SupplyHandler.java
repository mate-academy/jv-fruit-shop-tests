package core.basesyntax.service.handles;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;

public class SupplyHandler implements OperationHandler {
    @Override
    public void handler(FruitTransaction transaction) {
        if (Storage.storage.containsKey(transaction.getFruit())) {
            Integer quantity = Storage.storage.get(transaction.getFruit());
            Storage.storage.put(transaction.getFruit(), quantity + transaction.getQuantity());
        } else {
            Storage.storage.put(transaction.getFruit(), transaction.getQuantity());
        }
    }
}
