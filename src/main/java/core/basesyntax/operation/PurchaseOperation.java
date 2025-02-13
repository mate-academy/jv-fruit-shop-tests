package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(FruitTransaction transaction) {
        validateTransaction(transaction);
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        int storageQuantity = Storage.getFruitStorage().getOrDefault(fruit, 0);
        if (quantity > storageQuantity) {
            throw new IllegalArgumentException("We don't have " + quantity + fruit
                    + "'s . we have only " + storageQuantity);
        }
        Storage.getFruitStorage().put(fruit, storageQuantity - quantity);
    }
}
