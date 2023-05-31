package core.basesyntax.service.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class ReturnOperationHandler implements OperationHandler {
    @Override
    public void handle(FruitTransaction transaction) {
        int currentFruitQuantity = Storage.fruits.get(transaction.getFruitName());
        int transactionFruitQuantity = transaction.getQuantity();
        if (transactionFruitQuantity > 0) {
            Storage.fruits.put(transaction.getFruitName(),
                    currentFruitQuantity + transactionFruitQuantity);
        } else {
            throw new RuntimeException("You can't return "
                    + transaction.getFruitName() + " in quantity"
                    + transactionFruitQuantity + " to the Fruit shop");
        }
    }
}
