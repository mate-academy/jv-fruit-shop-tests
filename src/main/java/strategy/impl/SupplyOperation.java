package strategy.impl;

import model.FruitTransaction;
import strategy.OperationHandler;

public class SupplyOperation implements OperationHandler {
    @Override
    public int getOperation(FruitTransaction data, int amount) {
        if (data == null) {
            throw new RuntimeException(
                    "Data cannot be null");
        }
        if (amount < 0) {
            throw new RuntimeException(
                    "Amount for supply operation cannot be negative: " + data.getFruit());
        }
        if (data.getQuantity() < 0) {
            throw new RuntimeException(
                    "Data quantity cannot be negative!");
        }
        return data.getQuantity() + amount;
    }
}
