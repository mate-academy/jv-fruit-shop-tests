package core.basesyntax.service.exceptions;

public class EmptyStorageException extends RuntimeException {
    public EmptyStorageException(String message) {
        super(message);
    }
}
