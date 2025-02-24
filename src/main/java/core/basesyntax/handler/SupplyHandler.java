package core.basesyntax.handler;

import core.basesyntax.database.Storage;
import core.basesyntax.transactor.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        int amountAfterSupply = Storage.storage.get(transaction.getFruit())
                + transaction.getQuantity();
        Storage.storage.put(transaction.getFruit(),amountAfterSupply);
    }
}
