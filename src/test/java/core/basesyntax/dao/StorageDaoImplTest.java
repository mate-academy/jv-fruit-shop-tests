package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final Integer AMOUNT = 100;
    private static final String FRUIT_NAME = "banana";
    private static final String NAME_OF_NOT_EXISTING_FRUIT = "grape";
    private static StorageDao storageDao;
    private static Map<Fruit, Integer> expectedMap;
    private Fruit fruit;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        expectedMap = new HashMap<>();

    }

    @BeforeEach
    void setUp() {
        fruit = new Fruit(FRUIT_NAME);
        expectedMap.put(fruit, AMOUNT);
    }

    @Test
    void storageDao_add_isOk() {
        storageDao.add(fruit, AMOUNT);
        Map<Fruit, Integer> actualMap = Storage.fruits;
        assertEquals(expectedMap, actualMap);
    }

    @Test
    void storageDao_getExistingFruit_isOk() {
        Storage.fruits.put(fruit, AMOUNT);
        Map.Entry<Fruit, Integer> actualEntry = storageDao.get(fruit.getName());
        Fruit actualFruit = actualEntry.getKey();
        Integer actualAmount = actualEntry.getValue();
        assertEquals(fruit, actualFruit);
        assertEquals(AMOUNT, actualAmount);
    }

    @Test
    void storageDao_getNullIfNotExist_isOk() {
        Storage.fruits.put(fruit, AMOUNT);
        Map.Entry<Fruit, Integer> actualEntry = storageDao.get(NAME_OF_NOT_EXISTING_FRUIT);
        assertNull(actualEntry);
    }

    @Test
    void storageDao_getAll_isOk() {
        Storage.fruits.put(fruit, AMOUNT);
        Map<Fruit, Integer> actualMap = storageDao.getALl();
        assertEquals(expectedMap, actualMap);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }
}
