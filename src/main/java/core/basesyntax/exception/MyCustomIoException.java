package core.basesyntax.exception;

public class MyCustomIoException extends RuntimeException {
    public MyCustomIoException(String message, Throwable cause) {
        super(message, cause);
    }
}
