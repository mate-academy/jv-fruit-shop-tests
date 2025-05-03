package core.basesyntax.db;

public class FruitDaoException extends RuntimeException {
    private static final String ERROR_MESSAGE = "Fruit stock cannot be less than 0";

    public FruitDaoException() {
        super(ERROR_MESSAGE);
    }
}
