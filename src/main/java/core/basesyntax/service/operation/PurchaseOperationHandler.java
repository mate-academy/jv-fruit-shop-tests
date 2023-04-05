package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentFruitQuantity = Storage.fruits.get(transaction.getFruitName());
        int transactionFruitQuantity = transaction.getQuantity();
        if (currentFruitQuantity >= transactionFruitQuantity
                && transactionFruitQuantity > 0) {
            Storage.fruits.put(transaction.getFruitName(),
                    currentFruitQuantity - transactionFruitQuantity);
        } else {
            throw new RuntimeException("Fruit shop can't purchase you "
                    + transaction.getFruitName() + " in quantity "
                    + transactionFruitQuantity + " because shop has only "
                    + currentFruitQuantity + " fruits");
        }
    }
}
