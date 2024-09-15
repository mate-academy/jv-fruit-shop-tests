package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class BalanceOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        if (transaction.getQuantity() >= 0) {
            Storage.fruits.put(transaction.getFruitName(), transaction.getQuantity());
        } else {
            throw new RuntimeException("Quantity is less than 0");
        }
    }
}
