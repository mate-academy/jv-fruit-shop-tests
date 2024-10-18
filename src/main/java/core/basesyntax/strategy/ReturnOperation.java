package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class ReturnOperation implements FruitOperationHandler {
    @Override
    public void executeOperation(FruitTransaction transaction, Map<String, Integer> inventory) {
        if (transaction == null) {
            throw new NullPointerException("Transaction cannot be null");
        }

        String fruit = transaction.getFruit();
        if (fruit == null) {
            throw new RuntimeException("Cannot complete return operation: "
                    + "fruit name cannot be null");
        }

        int quantity = transaction.getQuantity();
        if (quantity < 0) {
            throw new RuntimeException("Cannot complete return operation: "
                    + "negative stock for " + fruit);
        }

        int currentBalance = inventory.getOrDefault(fruit, 0);
        inventory.put(fruit, currentBalance + quantity);
    }
}
