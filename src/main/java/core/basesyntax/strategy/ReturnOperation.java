package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class ReturnOperation implements FruitOperationHandler {
    @Override
    public void executeOperation(FruitTransaction transaction, Map<String, Integer> inventory) {
        int currentBalance = inventory.getOrDefault(transaction.getFruit(), 0);

        if (transaction.getQuantity() < 0) {
            throw new RuntimeException(
                    "Cannot complete return operation: negative stock for "
                            + transaction.getFruit());
        }

        if (transaction.getFruit() == null) {
            throw new RuntimeException(
                    "Cannot complete return operation: negative stock for "
                            + transaction.getFruit());
        }

        inventory.put(transaction.getFruit(), currentBalance + transaction.getQuantity());
    }
}
