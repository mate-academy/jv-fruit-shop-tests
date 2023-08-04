package core.basesyntax.strategy;

import core.basesyntax.service.strategy.BalanceOperation;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.storage.Storage;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String FRUIT = "fruit";
    private static final int QUANTITY = 10;
    private static final int NEGATIVE_QUANTITY = -1;
    private static OperationStrategy strategy;
    private static Map<String,Integer> validArguments;

    @BeforeAll
    static void beforeAll() {
        strategy = new BalanceOperation();
        validArguments = Map.of(
                "banana", 0,
                "apple", 1,
                "pineapple", 1000000,
                "blueberry", Integer.MAX_VALUE);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.clear();
    }

    @Test
    void apply_validArguments_ok() {
        strategy.apply(FRUIT, QUANTITY);
        int result = Storage.fruits.get(FRUIT);
        Assertions.assertEquals(QUANTITY, result);
    }

    @Test
    void apply_negativeQuantity_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> strategy.apply(FRUIT, NEGATIVE_QUANTITY),
                "IllegalArgument Exception must be thrown if quantity is negative");
    }
    
    @Test
    void apply_edgeValidArguments_ok() {
        validArguments.forEach(strategy::apply);
        Assertions.assertEquals(validArguments, Storage.fruits);
    }
}
