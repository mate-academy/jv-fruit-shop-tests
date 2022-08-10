package core.basesyntax.operations;

import core.basesyntax.FruitTransaction;
import core.basesyntax.storage.Storage;

public class PurchaseOperation implements Operation {
    @Override
    public void performOperation(Storage storage, FruitTransaction fruitTransaction) {
        storage.removeFromStorage(fruitTransaction.getFruit(), fruitTransaction.getCount());
    }
}
