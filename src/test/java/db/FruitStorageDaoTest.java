package db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoTest {
    private static final FruitStorage FRUIT_STORAGE = new FruitStorage();
    private static final Map<String, Integer> testFruitsMap = Map.of(
            "banana", 50,
            "apple", 60);
    private static final StorageDao<String, Integer> FRUIT_STORAGE_DAO =
            new FruitStorageDao(FRUIT_STORAGE);
    private static final String TEST_FRUIT_NAME = "banana";
    private static final Integer TEST_FRUIT_COUNT = 50;

    @BeforeEach
    void setUp() {
        for (Map.Entry<String, Integer> entry : testFruitsMap.entrySet()) {
            FRUIT_STORAGE_DAO.put(entry.getKey(), entry.getValue());
        }
    }

    @Test
    void put_Fruits_Ok() {
        for (Map.Entry<String, Integer> entry : testFruitsMap.entrySet()) {
            FRUIT_STORAGE_DAO.put(entry.getKey(), entry.getValue());
        }
        assertEquals(testFruitsMap, FRUIT_STORAGE.getFruits());
    }

    @Test
    void remove_Fruit_Ok() {
        boolean actual = FRUIT_STORAGE_DAO.remove(TEST_FRUIT_NAME, TEST_FRUIT_COUNT);
        assertTrue(actual);
    }

    @Test
    void remove_notExistFruit_NotOk() {
        boolean actual = FRUIT_STORAGE_DAO.remove("badName", 999);
        assertFalse(actual);
    }

    @Test
    void getValue_fruitCount_Ok() {
        Integer actual = FRUIT_STORAGE_DAO.getValue(TEST_FRUIT_NAME);
        assertEquals(actual, TEST_FRUIT_COUNT);
    }

    @Test
    void getValue_notExistFruitCount_NotOk() {
        Integer actual = FRUIT_STORAGE_DAO.getValue("badName");
        assertNull(actual);
    }
}
