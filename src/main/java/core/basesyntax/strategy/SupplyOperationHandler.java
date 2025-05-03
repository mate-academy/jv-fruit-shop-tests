package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void operate(FruitTransaction transaction) {
        int quantitySupply = Storage.fruits.get(transaction.getFruit()) + transaction.getQuantity();
        Storage.fruits.put(transaction.getFruit(), quantitySupply);
    }
}
