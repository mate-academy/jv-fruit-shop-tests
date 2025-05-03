package core.basesyntax.model;

public interface OperationHandler {
    Integer handle(Integer quantity);

    default void exceptionCheck(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException(this.getClass().getSimpleName().replace("Operation", "")
                    + " operation requires a positive quantity, but given: "
                    + quantity);
        }
    }
}
