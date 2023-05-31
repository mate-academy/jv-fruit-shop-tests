package core.basesyntax.strategy.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction fruitTransaction) {
        String fruitInStorage = fruitTransaction.getFruit();
        int supplyQty = fruitTransaction.getQuantity();
        int currentQtyInStorage = Storage.fruitsStorage.get(fruitInStorage);
        Storage.fruitsStorage.put(fruitInStorage, (currentQtyInStorage + supplyQty));
    }
}
