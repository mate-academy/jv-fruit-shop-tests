package core.basesyntax.exception;

public class MyCustomIOException extends RuntimeException {
    public MyCustomIOException(String message, Throwable cause) {
        super(message, cause);
    }
}
