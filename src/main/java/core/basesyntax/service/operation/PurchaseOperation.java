package core.basesyntax.service.operation;

import java.util.Map;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(String fruit, int quantity, Map<String, Integer> storage) {
        int currentQuantity = storage.getOrDefault(fruit, 0);
        if (currentQuantity < quantity) {
            throw new IllegalStateException("Not enough " + fruit + " to complete the purchase.");
        }
        storage.put(fruit, currentQuantity - quantity);
    }
}
