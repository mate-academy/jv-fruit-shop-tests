package core.basesyntax.exception;

public class NoSuchFileException extends RuntimeException {
    public NoSuchFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchFileException(String message) {
        super(message);
    }
}
