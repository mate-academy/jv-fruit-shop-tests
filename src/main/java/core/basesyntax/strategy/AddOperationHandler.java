package core.basesyntax.strategy;

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
        inventoryService.addFruit(fruit, quantity);
    }
}
