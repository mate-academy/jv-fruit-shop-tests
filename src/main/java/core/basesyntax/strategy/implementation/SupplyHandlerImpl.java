package core.basesyntax.strategy.implementation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;

public class SupplyHandlerImpl implements OperationHandler {
    @Override
    public void processTransaction(FruitTransaction transaction) {
        Integer oldQuantity = Storage.getStorage().get(transaction.getFruit());
        Storage.getStorage().put(transaction.getFruit(), oldQuantity + transaction.getQuantity());
    }
}
