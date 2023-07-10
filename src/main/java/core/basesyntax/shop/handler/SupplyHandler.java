package core.basesyntax.shop.handler;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.model.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction transaction) {
        int currentValue = Storage.fruits.get(transaction.getFruitName());
        Storage.fruits.put(transaction.getFruitName(), currentValue + transaction.getQuantity());
    }
}
