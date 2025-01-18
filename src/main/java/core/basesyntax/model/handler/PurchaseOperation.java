package core.basesyntax.model.handler;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void handle(Map<String, Integer> storage, FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int currentQuantity = storage.getOrDefault(fruit, 0);
        int transactionQuantity = transaction.getQuantity();

        if (currentQuantity < transactionQuantity) {
            throw new IllegalArgumentException(
                    "Not enough " + fruit + " in storage. Current: "
                            + currentQuantity + ", required: " + transactionQuantity
            );
        }

        storage.put(fruit, currentQuantity - transactionQuantity);
    }
}
