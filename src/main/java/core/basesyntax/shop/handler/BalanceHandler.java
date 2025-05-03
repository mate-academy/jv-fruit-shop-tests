package core.basesyntax.shop.handler;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.model.FruitTransaction;

public class BalanceHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction transaction) {
        Storage.fruits.put(transaction.getFruitName(), transaction.getQuantity());
    }
}
