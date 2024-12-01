package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitDB;
import java.util.Map;

public class PurchaseHandler implements OperationHandler {

    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        Map<String, Integer> inventory = FruitDB.getInstance().getInventory();
        if (!inventory.containsKey(fruit) || inventory.get(fruit) < quantity) {
            throw new IllegalArgumentException("Not enough inventory to subtract");
        }
        inventory.put(fruit, inventory.get(fruit) - quantity);
    }
}
