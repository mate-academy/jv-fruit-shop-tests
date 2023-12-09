package core.basesyntax.exceptions;

public class NonExistingOperationException extends NumberFormatException {
    public NonExistingOperationException(String message) {
        super(message);
    }
}
