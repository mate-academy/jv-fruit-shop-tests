package core.basesyntax.store.strategy.impl;

import core.basesyntax.store.db.Storage;
import core.basesyntax.store.model.FruitTransaction;
import core.basesyntax.store.strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction fruitTransaction) {
        Storage.modifyFruitStorage(fruitTransaction.getFruit(), fruitTransaction.getQuantity());
    }
}
