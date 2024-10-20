package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.Map;

public class PurchaseOperation implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> storage, FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();

        int currentStock = storage.getOrDefault(fruit, 0);

        if (quantity < 0) {
            throw new RuntimeException("Cant be negative");
        }

        if (currentStock < quantity) {
            throw new IllegalArgumentException("Not enough product to purchase");
        }

        storage.put(fruit, currentStock - quantity);
    }
}
