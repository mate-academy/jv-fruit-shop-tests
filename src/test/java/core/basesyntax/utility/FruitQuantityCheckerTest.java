package core.basesyntax.utility;

import static org.junit.jupiter.api.Assertions.assertAll;
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
    void checkFruitQuantity_ValidData_Ok() {
        fruitMap.put(FIRS_KEY, 12);
        fruitMap.put(SECOND_KEY, 0);
        fruitQuantityChecker.checkFruitQuantity(fruitMap);
    }

    @Test
    void checkFruitQuantity_InvalidData_NotOk() {
        fruitMap.put(FIRS_KEY, -1);
        assertAll(
                () -> assertThrows(RuntimeException.class,
                        () -> fruitQuantityChecker.checkFruitQuantity(fruitMap)),
                () -> {
                    fruitMap.clear();
                    fruitMap.put(SECOND_KEY, null);
                    assertThrows(RuntimeException.class,
                            () -> fruitQuantityChecker.checkFruitQuantity(fruitMap));
                }
        );
    }
}
