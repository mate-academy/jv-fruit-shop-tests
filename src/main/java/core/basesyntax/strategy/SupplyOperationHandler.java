package core.basesyntax.strategy;

import java.util.Map;

public class SupplyOperationHandler implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity for supply cannot be negative");
        }
        inventory.put(fruit, inventory.getOrDefault(fruit, 0) + quantity);
    }
}
