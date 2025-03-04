package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.impl.CheckFruitExistImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CheckFruitExistTest {
    private static CheckFruitExistImpl checkFruitExist;
    private static String fruitOne;
    private static String fruitTwo;
    private static String fruitNull;
    private static Integer quantity;

    @BeforeAll
    static void beforeAll() {
        checkFruitExist = new CheckFruitExistImpl();
        fruitOne = "Banana";
        fruitTwo = "Apple";
        fruitNull = null;
        quantity = 10;
    }

    @AfterEach
    void tearDown() {
        FruitStorage.storage.clear();
    }

    @Test
    void checkFruitExist_nullFruit_notOk() {
        assertThrows(RuntimeException.class, () -> {
            checkFruitExist.check(fruitNull);
        });
    }

    @Test
    void checkFruitExist_nullFruitInStorage_Ok() {
        FruitStorage.storage.put(fruitNull, quantity);
        assertDoesNotThrow(() -> checkFruitExist.check(fruitNull));
    }

    @Test
    void checkFruitExist_FruitInStorage_notOk() {
        FruitStorage.storage.put(fruitOne, quantity);
        assertThrows(RuntimeException.class, () -> {
            checkFruitExist.check(fruitTwo);
        });
    }

    @Test
    void checkFruitExist_FruitInStorage_Ok() {
        FruitStorage.storage.put(fruitOne, quantity);
        assertDoesNotThrow(() -> checkFruitExist.check(fruitOne));
    }
}
