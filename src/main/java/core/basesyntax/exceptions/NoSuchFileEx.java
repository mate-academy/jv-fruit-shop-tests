package core.basesyntax.exceptions;

public class NoSuchFileEx extends RuntimeException {
    public NoSuchFileEx(String message, Throwable cause) {
        super(message, cause);
    }
}
