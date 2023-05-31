package core.basesyntax.exceptions;

public class NegativeTransactionException extends RuntimeException {
    public NegativeTransactionException(String message) {
        super(message);
    }
}
