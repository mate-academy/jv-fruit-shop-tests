package core.basesyntax.exceptions;

public class NoSuchCsvFileException extends RuntimeException {
    public NoSuchCsvFileException(String message) {
        super(message);
    }
}
