package core.basesyntax.operation;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void apply(FruitTransaction transaction);

    default void validateTransaction(FruitTransaction transaction) {
        if (transaction.getQuantity() < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: "
                    + transaction.getQuantity());
        }
    }
}
