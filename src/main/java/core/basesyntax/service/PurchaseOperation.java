package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        Storage.decrementQuantity(transaction.getFruit(), transaction.getQuantity());
    }

}
