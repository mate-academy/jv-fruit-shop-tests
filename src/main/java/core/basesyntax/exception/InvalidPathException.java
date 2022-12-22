package core.basesyntax.exception;

public class InvalidPathException extends RuntimeException {
    public InvalidPathException(String message) {
        super(message);
    }

    public InvalidPathException(String message, Throwable cause) {
        super(message, cause);
    }
}
