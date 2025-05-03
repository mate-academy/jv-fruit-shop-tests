package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.database.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static FruitDao fruitDao = new FruitDaoImpl();

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void put_correctFruit_Ok() {
        String expectedFruitName = "Apple";
        int expectedQuantity = 5;
        fruitDao.put(expectedFruitName, expectedQuantity);
        assertEquals(expectedQuantity, Storage.fruitsStorage.get(expectedFruitName));
    }

    @Test
    void getFruitQuantity_correctFruit_Ok() {
        String expectedFruitName = "Apple";
        int expectedQuantity = 5;
        fruitDao.put(expectedFruitName, expectedQuantity);
        assertEquals(fruitDao.getFruitQuantity(expectedFruitName), expectedQuantity);
    }

    @Test
    void getFruitQuantity_notExistingFruit_notOk() {
        String expectedFruitName = "Apple";
        assertThrows(RuntimeException.class,() -> fruitDao.getFruitQuantity(expectedFruitName));
    }

    @Test
    void getAll() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("Apple",1);
        expectedMap.put("Banana",2);
        expectedMap.put("Orange",3);
        expectedMap.put("Lemon",4);
        for (Map.Entry<String, Integer> element : expectedMap.entrySet()) {
            Storage.fruitsStorage.put(element.getKey(), element.getValue());
        }
        Map<String, Integer> actualMap = fruitDao.getAll();
        expectedMap.entrySet().stream()
                .forEach(e -> assertEquals(e.getValue(), actualMap.get(e.getKey())));
    }
}
