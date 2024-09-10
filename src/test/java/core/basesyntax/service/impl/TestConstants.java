package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import java.util.ArrayList;
import java.util.List;

public final class TestConstants {
    public static final FruitTransaction DEFAULT_APPLE_TRANSACTION =
            new FruitTransaction("apple", 20, FruitTransaction.Operation.BALANCE);
    public static final FruitTransaction DEFAULT_BANANA_TRANSACTION =
            new FruitTransaction("banana", 15, FruitTransaction.Operation.SUPPLY);
    public static final FruitTransaction DEFAULT_STRAWBERRY_TRANSACTION =
            new FruitTransaction("strawberry", 70, FruitTransaction.Operation.PURCHASE);
    public static final FruitTransaction DEFAULT_ORANGE_TRANSACTION =
            new FruitTransaction("orange", 42, FruitTransaction.Operation.RETURN);
    public static final List<String> DEFAULT_READED_STRING_LIST = new ArrayList<>(List.of(
            "    type,fruit,quantity",
            "    b,banana,20",
            "    b,apple,100",
            "    s,banana,100",
            "    p,banana,13",
            "    r,apple,10",
            "    p,apple,20",
            "    p,banana,5",
            "    s,banana,50"));
    public static final String DEFAULT_APPLE_STRING = "b,apple,20";
    public static final String DEFAULT_BANANA_STRING = "s,banana,15";
    public static final String DEFAULT_STRAWBERRY_STRING = "p,strawberry,70";
    public static final String DEFAULT_ORANGE_STRING = "r,orange,42";
    public static final String DEFAULT_RESULT_HEADER = "fruit,quantity";
    public static final String RESULT_SEPARATOR = ",";
    public static final String BANANA_TRANSACTION_RESULT_EXAMPLE = "banana,152";
    public static final String APPLE_TRANSACTION_RESULT_EXAMPLE = "apple,92";
    public static final int FRUIT_NAME_INDEX_IN_RESULT = 0;
    public static final int FRUIT_QUANTITY_INDEX_IN_RESULT = 1;
    public static final String INVALID_FILE_NAME_TO_READ = "invalidReport.csv";
    public static final String DEFAULT_FILE_NAME_TO_READ = "reportToRead.csv";
    public static final String DEFAULT_FILE_NAME_TO_WRITE = "finalReport.csv";

    private TestConstants() {
    }
}
