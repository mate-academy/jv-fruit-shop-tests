package core.basesyntax.utility;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FruitQuantityCheckerTest {
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
        fruitMap.put("dummy", 12);
        fruitMap.put("dummy2", 0);
        fruitQuantityChecker.checkFruitQuantity(fruitMap);
    }

    @Test
    void checkFruitQuantity_InvalidData_NotOk() {
        fruitMap.put("dummy", -1);
        assertAll(
                () -> assertThrows(RuntimeException.class,
                        () -> fruitQuantityChecker.checkFruitQuantity(fruitMap)),
                () -> {
                    fruitMap.clear();
                    fruitMap.put("dummy2", null);
                    assertThrows(RuntimeException.class,
                            () -> fruitQuantityChecker.checkFruitQuantity(fruitMap));
                }
        );
    }
}