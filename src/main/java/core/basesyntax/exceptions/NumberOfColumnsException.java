package core.basesyntax.exceptions;

public class NumberOfColumnsException extends RuntimeException {

    public NumberOfColumnsException(String message) {
        super(message);
    }

    public NumberOfColumnsException(String message, Throwable cause) {
        super(message, cause);
    }
}
