package core.basesyntax.exception;

public class ReadFileException extends RuntimeException {
    public ReadFileException() {
    }

    public ReadFileException(String message) {
        super(message);
    }
}
