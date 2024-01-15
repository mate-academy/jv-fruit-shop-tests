package core.basesyntax.services;

import core.basesyntax.models.FruitTransaction;

public class Constants {
    public static final String ORANGE = "orange";
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final String ANY_FRUIT = "anyFruit";
    public static final int INITIAL_QUANTITY_OF_ORANGE = 100;
    public static final int INITIAL_QUANTITY_OF_APPLE = 100;
    public static final int INITIAL_QUANTITY_OF_BANANA = 100;
    public static final FruitTransaction NO_SUCH_FRUIT_EXCEPTION_TRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.BALANCE, ANY_FRUIT, 10);
}
