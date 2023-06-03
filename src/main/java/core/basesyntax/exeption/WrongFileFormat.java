package core.basesyntax.exeption;

public class WrongFileFormat extends RuntimeException {
    public WrongFileFormat(String message) {
        super(message);
    }
}
