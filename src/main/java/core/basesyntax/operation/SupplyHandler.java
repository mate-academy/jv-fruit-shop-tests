package core.basesyntax.operation;

import core.basesyntax.storage.FruitStorage;
import core.basesyntax.transaction.FruitTransaction;

public class SupplyHandler implements OperationHandler {
    private FruitStorage fruitStorage;

    public SupplyHandler(FruitStorage fruitStorage) {
        this.fruitStorage = fruitStorage;
    }

    @Override
    public void apply(FruitTransaction transaction) {
        String fruit = transaction.getFruit();
        int quantity = transaction.getQuantity();
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity for supply cannot be negative: "
                    + quantity);
        }
        fruitStorage.supplyFruits(fruit, quantity);
    }
}
