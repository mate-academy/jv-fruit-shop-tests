package core.basesyntax.exceptions;

public class NonNumericDataException extends IllegalArgumentException {
    public NonNumericDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
