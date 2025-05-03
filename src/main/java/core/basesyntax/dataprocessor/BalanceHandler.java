package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitDB;
import java.util.Map;

public class BalanceHandler implements OperationHandler {

    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        Map<String, Integer> inventory = FruitDB.getInventory();
        inventory.put(fruit, inventory.getOrDefault(fruit, 0) + quantity);
    }
}
