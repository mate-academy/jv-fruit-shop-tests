package core.basesyntax.strategy;

import core.basesyntax.service.InventoryService;
import java.util.Map;

public class AddOperationHandler implements OperationHandler {

    private final InventoryService inventoryService;

    public AddOperationHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        int currentQuantity = inventory.getOrDefault(fruit, 0);
        if (currentQuantity < quantity) {
            throw new IllegalArgumentException("Not enough " + fruit + " in inventory");
        }
        inventory.put(fruit, currentQuantity - quantity);
    }
}
