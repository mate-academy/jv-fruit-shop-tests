package core.basesyntax.exception;

public class ParserServiceException extends RuntimeException {
    public ParserServiceException(String message) {
        super(message);
    }

    public ParserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
