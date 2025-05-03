package core.basesyntax.exception;

public class FruitTransactionException extends RuntimeException {
    public FruitTransactionException(String message) {
        super(message);
    }

    public FruitTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
