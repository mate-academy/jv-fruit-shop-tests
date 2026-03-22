package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {

    @Override
    public void apply(FruitTransaction transaction) {
        Storage.incrementQuantity(transaction.getFruit(), transaction.getQuantity());
    }
}