package core.basesyntax.strategy;

public class ElementDoesNotExist extends RuntimeException {
    public ElementDoesNotExist(String message) {
        super(message);
    }
}
