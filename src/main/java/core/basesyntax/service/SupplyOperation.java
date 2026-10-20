package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void process(FruitTransaction transaction) {
        Storage.fruits.merge(transaction.getFruit(), transaction.getQuantity(), Integer::sum);
    }
}

