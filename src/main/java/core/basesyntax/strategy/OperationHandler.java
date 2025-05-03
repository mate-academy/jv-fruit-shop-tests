package core.basesyntax.strategy;

public interface OperationHandler {
    static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative: " + quantity);
        }
    }

    void handle(FruitTransaction transaction);
}
