package core.basesyntax.operations;

import core.basesyntax.FruitTransaction;
import core.basesyntax.storage.Storage;

public class ReturnOperation implements Operation {
    @Override
    public void performOperation(Storage storage, FruitTransaction fruitTransaction) {
        storage.add(fruitTransaction.getFruit(), fruitTransaction.getCount());
    }
}
