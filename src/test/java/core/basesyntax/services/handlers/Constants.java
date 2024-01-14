package core.basesyntax.services.handlers;

import core.basesyntax.models.FruitTransaction;

public class Constants {
    public static final String ORANGE = "orange";
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final int INITIAL_QUANTITY_OF_ORANGE = 100;
    public static final int INITIAL_QUANTITY_OF_APPLE = 100;
    public static final int INITIAL_QUANTITY_OF_BANANA = 100;
    public static final FruitTransaction ORANGE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, ORANGE, 10);
    public static final FruitTransaction APPLE_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 10);
    public static final FruitTransaction BANANA_FRUITTRANSACTION =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 10);
    public static final FruitTransaction BANANA_FRUITTRANSACTION_NEGATIVE_QUANTITY =
            new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, -1);
}
