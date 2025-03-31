package core.basesyntax.service.strategy;

public interface OperationHandler {
    int getOperationType(int quantity);

    static void validateQuantity(int quantity) {
        if (quantity < 0) {
            throw new RuntimeException("Invalid data, quantity can't be negative: "
                    + quantity);
        }
    }
}
