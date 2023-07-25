package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {

    @Override
    public int apply(FruitTransaction fruitTransaction) {
        int previousValue = Storage.fruits.get(fruitTransaction.getFruit());
        int newValue = previousValue - fruitTransaction.getQuantity();
        Storage.fruits.put(fruitTransaction.getFruit(), newValue);
        return newValue;
    }
}
