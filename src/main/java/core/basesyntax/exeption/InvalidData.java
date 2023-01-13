package core.basesyntax.exeption;

public class InvalidData extends RuntimeException {
    public InvalidData(String message) {
        super(message);
    }
}
