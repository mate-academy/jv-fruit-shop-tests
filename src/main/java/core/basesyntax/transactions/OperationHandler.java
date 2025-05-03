package core.basesyntax.transactions;

public interface OperationHandler {
    void resultOfOperation(String fruitName, int amount);

    default void checkParameters(String fruitName, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(" The amount can`t be less or equals zero");
        } else if (fruitName == null) {
            throw new IllegalArgumentException(" The fruit can`t be null");
        }
    }
}
