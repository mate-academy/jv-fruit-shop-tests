package core.basesyntax.strategy;

import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.SupplyOperation;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String FRUIT = "fruit";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static OperationStrategy strategy;
    private static Map<String, Integer> storageContent;
    private static Map<String, Integer> validArguments;
    private static Map<String, Integer> expectedResult;

    @BeforeAll
    static void beforeAll() {
        strategy = new SupplyOperation();
        storageContent = Map.of(
                "banana", 5,
                "apple", 12,
                "pineapple", 1000000,
                "blueberry", 0);
        validArguments = Map.of(
                "banana", 0,
                "apple", 21,
                "pineapple", 2604,
                "blueberry", Integer.MAX_VALUE);
        expectedResult = Map.of(
                "banana", 5,
                "apple", 33,
                "pineapple", 1002604,
                "blueberry", Integer.MAX_VALUE);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_negativeQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, NEGATIVE_QUANTITY),
                "IllegalArgumentException must be thrown if provided quantity is negative");
    }

    @Test
    void apply_storageOverflows_notOk() {
        Storage.fruits.put(FRUIT, QUANTITY);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, Integer.MAX_VALUE));
    }

    @Test
    void apply_validArguments_ok() {
        Storage.fruits.putAll(storageContent);
        validArguments.forEach(strategy::apply);
        Assertions.assertEquals(expectedResult, Storage.fruits);
    }
}
