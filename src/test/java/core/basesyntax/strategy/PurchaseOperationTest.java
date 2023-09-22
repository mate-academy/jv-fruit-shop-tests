package core.basesyntax.strategy;

import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.PurchaseOperation;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String FRUIT = "fruit";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static OperationStrategy strategy;
    private static Map<String,Integer> storageContent;
    private static Map<String, Integer> validArguments;
    private static Map<String, Integer> expectedResult;

    @BeforeAll
    static void beforeAll() {
        strategy = new PurchaseOperation();
        storageContent = Map.of(
                "banana", 5,
                "apple", 12,
                "pineapple", 1000000,
                "blueberry", Integer.MAX_VALUE);
        validArguments = Map.of(
                "banana", 2,
                "apple", 1,
                "pineapple", 140419,
                "blueberry", Integer.MAX_VALUE);
        expectedResult = Map.of(
                "banana", 3,
                "apple", 11,
                "pineapple", 859581,
                "blueberry", 0);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_notExistFruit_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, QUANTITY),
                "IllegalArgumentException must be thrown if fruit is not exist in Storage");
    }

    @Test
    void apply_negativeQuantity_notOk() {
        Storage.fruits.put(FRUIT, QUANTITY);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, NEGATIVE_QUANTITY),
                "IllegalArgumentException must be thrown for negative fruit purchase amount");
    }

    @Test
    void apply_amountMoreThanThereAre_notOk() {
        Storage.fruits.put(FRUIT, QUANTITY);
        int biggerValue = QUANTITY << 1;
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, biggerValue),
                "IllegalArgumentException must be thrown if purchase amount more than Storage has");
    }

    @Test
    void apply_validArguments_ok() {
        Storage.fruits.putAll(storageContent);
        validArguments.forEach(strategy::apply);
        Assertions.assertEquals(expectedResult, Storage.fruits);
    }
}
