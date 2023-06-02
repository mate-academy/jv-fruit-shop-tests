package core.basesyntax.exception;

public class WrongFileType extends RuntimeException {
    public WrongFileType(String message) {
        super(message);
    }
}
