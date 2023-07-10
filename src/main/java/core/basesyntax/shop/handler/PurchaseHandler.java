package core.basesyntax.shop.handler;

import core.basesyntax.shop.db.Storage;
import core.basesyntax.shop.model.FruitTransaction;

public class PurchaseHandler implements OperationHandler {
    @Override
    public void operation(FruitTransaction transaction) {
        int result = Storage.fruits.get(transaction.getFruitName()) - transaction.getQuantity();
        if (result >= 0) {
            Storage.fruits.put(transaction.getFruitName(), result);
        } else {
            throw new RuntimeException("Not enough fruits!");
        }
    }
}
