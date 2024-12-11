package core.basesyntax.data.exeption;

public class UnknownOperationException extends RuntimeException {
    public UnknownOperationException(String message, Throwable cause) {
        super(message,cause);
    }
}
