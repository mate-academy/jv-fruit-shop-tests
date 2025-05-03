package core.basesyntax.handlers;

public interface OperationHandler {
    void operate(String fruitName, int quantity);

    default void checkQuantity(int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Quantity cannot be negative" + quantity);
        }
    }
}
