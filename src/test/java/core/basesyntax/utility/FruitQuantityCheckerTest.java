package core.basesyntax.utility;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitQuantityCheckerTest {
    private static final String FIRS_KEY = "dummy";
    private static final String SECOND_KEY = "dummy2";
    private static FruitQuantityChecker fruitQuantityChecker;
    private static Map<String, Integer> fruitMap;

    @BeforeAll
    static void beforeAll() {
        fruitQuantityChecker = new FruitQuantityChecker();
        fruitMap = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        fruitMap.clear();
    }

    @Test
    void checkFruitQuantity_validData_ok() {
        fruitMap.put(FIRS_KEY, 12);
        fruitMap.put(SECOND_KEY, 0);
        fruitQuantityChecker.checkFruitQuantity(fruitMap);
    }

    @Test
    void checkFruitQuantity_negativeQuantityData_notOk() {
        fruitMap.put(FIRS_KEY, -1);
        assertThrows(RuntimeException.class,
                () -> fruitQuantityChecker.checkFruitQuantity(fruitMap));
    }

    @Test
    void checkFruitQuantity_nullQuantityData_notOk() {
        fruitMap.put(SECOND_KEY, null);
        assertThrows(RuntimeException.class,
                () -> fruitQuantityChecker.checkFruitQuantity(fruitMap));
    }
}
