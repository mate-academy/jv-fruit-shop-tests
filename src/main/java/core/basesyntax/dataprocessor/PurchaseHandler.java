package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitDB;

public class PurchaseHandler implements OperationHandler {
    private final FruitDB fruitDB;

    public PurchaseHandler(FruitDB fruitDB) {
        this.fruitDB = fruitDB;
    }

    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        if (quantity > fruitDB.getInventory().getOrDefault(fruit, 0)) {
            throw new IllegalArgumentException("Not enough inventory for purchase");
        }
        fruitDB.subtract(fruit, quantity);
    }
}
