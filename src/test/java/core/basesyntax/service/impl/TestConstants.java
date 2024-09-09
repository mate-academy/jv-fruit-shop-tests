package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;

public final class TestConstants {
    public static final FruitTransaction DEFAULT_APPLE_TRANSACTION =
            new FruitTransaction("apple", 20, FruitTransaction.Operation.BALANCE);
    public static final FruitTransaction DEFAULT_BANANA_TRANSACTION =
            new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY);
    public static final FruitTransaction DEFAULT_STRAWBERRY_TRANSACTION =
            new FruitTransaction("strawberry", 70, FruitTransaction.Operation.PURCHASE);
    public static final FruitTransaction DEFAULT_ORANGE_TRANSACTION =
            new FruitTransaction("orange", 42, FruitTransaction.Operation.RETURN);
    public static final String DEFAULT_APPLE_STRING = "b,apple,20";
    public static final String DEFAULT_BANANA_STRING = "s,banana,15";
    public static final String DEFAULT_STRAWBERRY_STRING = "p,strawberry,70";
    public static final String DEFAULT_ORANGE_STRING = "r,orange,42";
    public static final String INVALID_FILE_NAME_TO_READ = "invalidReport.csv";
    public static final String DEFAULT_FILE_NAME_TO_READ = "reportToRead.csv";
    public static final String DEFAULT_FILE_NAME_TO_WRITE = "finalReport.csv";
}
