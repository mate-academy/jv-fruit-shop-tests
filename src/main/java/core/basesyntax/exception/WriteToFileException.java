package core.basesyntax.exception;

public class WriteToFileException extends RuntimeException {
    public WriteToFileException() {
    }

    public WriteToFileException(String message) {
        super(message);
    }
}
