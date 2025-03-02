package core.basesyntax.strategy;

import core.basesyntax.Storage;
import core.basesyntax.service.InventoryService;
import java.util.Map;

public class AddOperationHandler implements OperationHandler {
    @Override
    public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
        Storage.inventory.put(fruit, Storage.inventory.getOrDefault(fruit, 0) + quantity);
    }

    public static class SubtractOperationHandler implements OperationHandler {
        @Override
        public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
            Storage.inventory.put(fruit, Storage.inventory.getOrDefault(fruit, 0) - quantity);
        }
    }

    public static class SupplyOperationHandler implements OperationHandler {
        @Override
        public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
            InventoryService inventoryService = new InventoryService(inventory);
            inventoryService.addFruit(fruit, quantity);
        }
    }

    public static class ReturnOperationHandler implements OperationHandler {
        @Override
        public void apply(Map<String, Integer> inventory, String fruit, int quantity) {
            Storage.inventory.put(fruit, Storage.inventory.getOrDefault(fruit, 0) + quantity);
        }
    }
}
