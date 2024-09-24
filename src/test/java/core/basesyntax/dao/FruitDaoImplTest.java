package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private static FruitDao fruitDao;
    private Map<String, Integer> storageMap;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
    }

    @BeforeEach
    void setUp() {
        storageMap = Storage.fruits;
        storageMap.clear();
    }

    @AfterEach
    void tearDown() {
        storageMap.clear();
    }

    @Test
    void addAndGetFruit_Ok() {
        storageMap.put("banana", 10);
        assertEquals(10, storageMap.get("banana"));
    }

    @Test
    void addAndGetMultipleFruits_Ok() {
        storageMap.put("banana", 10);
        storageMap.put("apple", 20);
        assertEquals(10, storageMap.get("banana"));
        assertEquals(20, storageMap.get("apple"));
    }

    @Test
    void fruitDaoAddAndGet_Ok() {
        fruitDao.add("banana", 15);
        fruitDao.add("apple", 25);
        assertEquals(15, fruitDao.get("banana"));
        assertEquals(25, fruitDao.get("apple"));
    }
}
