package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        int oldQuantity = Storage.dataBase.get(transaction.getFruit());
        Storage.dataBase.put(transaction.getFruit(), oldQuantity + transaction.getQuantity());
    }
}
