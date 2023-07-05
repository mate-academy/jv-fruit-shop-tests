package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.database.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static String expectedFruitName;
    private static Integer expectedQuantity;
    private static FruitDao fruitDao = new FruitDaoImpl();

    @BeforeAll
    static void beforeAll() {
        expectedFruitName = "Apple";
        expectedQuantity = 5;
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void put_correctFruit_Ok() {
        fruitDao.put(expectedFruitName, expectedQuantity);
        assertTrue(Storage.fruitsStorage.containsKey(expectedFruitName));
    }

    @Test
    void getFruitQuantity_correctFruit_Ok() {
        fruitDao.put(expectedFruitName, expectedQuantity);
        assertEquals(fruitDao.getFruitQuantity(expectedFruitName), expectedQuantity);
    }

    @Test
    void getFruitQuantity_notExistingFruit_notOk() {
        assertThrows(RuntimeException.class,() -> fruitDao.getFruitQuantity(expectedFruitName));
    }

    @Test
    void getAll() {
        Storage.fruitsStorage.put(expectedFruitName, expectedQuantity);
        Map<String, Integer> actualMap = fruitDao.getAll();
        assertSame(actualMap.get(expectedFruitName), expectedQuantity);
    }
}
