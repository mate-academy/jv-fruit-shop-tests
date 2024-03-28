package core.basesyntax.exceptions;

public class FileFoundException extends RuntimeException {
    public FileFoundException(String message, Throwable ex) {
        super(message);
    }
}
