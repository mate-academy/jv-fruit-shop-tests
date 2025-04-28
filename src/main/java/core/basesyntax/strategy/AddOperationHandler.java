package core.basesyntax.strategy;

import static core.basesyntax.db.Storage.inventory;

import core.basesyntax.service.InventoryService;

public class AddOperationHandler implements OperationHandler {

    private final InventoryService inventoryService;

    public AddOperationHandler(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Override
    public void apply(String fruit, int quantity) {
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
