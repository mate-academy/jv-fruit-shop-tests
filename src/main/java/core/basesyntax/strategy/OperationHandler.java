package core.basesyntax.strategy;

public interface OperationHandler {
    int operate(int oldQuantity, int newQuantity);

    default void checkQuantity(int oldQuantity, int newQuantity) {
        if (oldQuantity < 0 || newQuantity < 0) {
            throw new RuntimeException("Quantity is negative");
        }
    }
}
