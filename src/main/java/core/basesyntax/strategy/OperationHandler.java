package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;

public interface OperationHandler {
    void handle(FruitTransaction transaction);

    default void checkTransaction(FruitTransaction transaction) {
        if (transaction.getFruit() == null) {
            throw new RuntimeException("Fruit is null");
        }
        if (transaction.getOperation() == null) {
            throw new RuntimeException("Operation is null");
        }
        if (transaction.getQuantity() < 0) {
            throw new RuntimeException("Quantity is less than zero");
        }
    }
}
