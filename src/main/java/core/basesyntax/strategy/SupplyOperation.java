package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        int currentQuantity = Storage.getQuantity(transaction.getFruit());
        Storage.add(transaction.getFruit(), currentQuantity + transaction.getQuantity());
    }
}
