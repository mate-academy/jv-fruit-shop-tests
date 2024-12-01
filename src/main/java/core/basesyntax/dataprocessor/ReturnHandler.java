package core.basesyntax.dataprocessor;

import core.basesyntax.service.FruitDB;

public class ReturnHandler implements OperationHandler {

    @Override
    public void apply(String fruit, int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }
        FruitDB.getInstance().add(fruit, quantity);
    }
}
