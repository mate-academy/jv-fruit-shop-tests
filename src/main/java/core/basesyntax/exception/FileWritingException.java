package core.basesyntax.exception;

public class FileWritingException extends RuntimeException {
    public FileWritingException(String message) {
        super(message);
    }

    public FileWritingException(String message, Throwable cause) {
        super(message, cause);
    }
}
