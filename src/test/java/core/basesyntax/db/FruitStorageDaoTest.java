package core.basesyntax.db;

import core.basesyntax.errors.DaoError;
import java.lang.reflect.Field;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitStorageDaoTest {
    private static FruitStorageDao fruitStorageDao;
    private static Map<String, Integer> db;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        // Get FruitStorageDaoImpl private DB tests
        try {
            Field field = fruitStorageDao.getClass().getDeclaredField("db");
            field.setAccessible(true);
            db = (Map<String, Integer>) field.get(fruitStorageDao);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void increment_nullFruitName_notOk() {
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.increment(null, 10));
    }

    @Test
    void increment_emptyFruitName_notOk() {
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.increment("", 10));
    }

    @Test
    void increment_invalidCount_notOk() {
        String fruitName = "banana";
        // Zero count
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.increment(fruitName, 0));
        // Negative count
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.increment(fruitName, -10));
    }

    @Test
    void increment_validData_ok() {
        db.clear();
        String fruitName = "banana";
        int expectedCount = 100;
        fruitStorageDao.increment(fruitName, expectedCount);
        int actualCount = db.get(fruitName);
        Assertions.assertEquals(expectedCount, actualCount,
                "Does not match the fruits count: expected "
                        + expectedCount + ", exist " + actualCount);
        expectedCount = 150;
        fruitStorageDao.increment(fruitName, 50);
        actualCount = db.get(fruitName);
        Assertions.assertEquals(expectedCount, actualCount,
                "Does not match the fruits count: expected "
                        + expectedCount + ", exist " + actualCount);
    }

    @Test
    void decrement_nullFruitName_notOk() {
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.decrement(null, 10));
    }

    @Test
    void decrement_emptyFruitName_notOk() {
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.decrement("", 10));
    }

    @Test
    void decrement_invalidCount_notOk() {
        // Zero count
        Assertions.assertThrows(DaoError.class, () -> fruitStorageDao.decrement("banana", 0));
        // Negative count
        Assertions.assertThrows(DaoError.class, () -> fruitStorageDao.decrement("banana", -10));
    }

    @Test
    void decrement_validData_ok() {
        db.clear();
        String fruitName = "banana";
        db.put(fruitName, 200);
        int expectedCount = 100;
        fruitStorageDao.decrement(fruitName, 100);
        int actualCount = db.get(fruitName);
        Assertions.assertEquals(expectedCount, actualCount,
                "Does not match the fruits count: expected "
                        + expectedCount + ", exist " + actualCount);
        expectedCount = 50;
        fruitStorageDao.decrement(fruitName, 50);
        actualCount = db.get(fruitName);
        Assertions.assertEquals(expectedCount, actualCount,
                "Does not match the fruits count: expected "
                        + expectedCount + ", exist " + actualCount);
        // Exceeding available quantity
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.decrement(fruitName, 100));
    }

    @Test
    void getCount_nullFruitName_notOk() {
        db.clear();
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.getCount(null));
    }

    @Test
    void getCount_emptyFruitName_notOk() {
        db.clear();
        Assertions.assertThrows(DaoError.class,
                () -> fruitStorageDao.getCount(""));
    }

    @Test
    void getCount_validData_ok() {
        db.clear();
        int expectedCount = 100;
        db.put("banana", expectedCount);
        int actualCount = fruitStorageDao.getCount("banana");
        Assertions.assertEquals(expectedCount, actualCount,
                "Does not match the fruits count: expected "
                        + expectedCount + ", exist " + actualCount);
    }

    @Test
    void getAllFruits_validData_ok() {
        db.clear();
        db.put("banana", 100);
        db.put("apple", 100);
        String[] actualFruits = fruitStorageDao.getAllFruits();
        Assertions.assertArrayEquals(db.keySet().toArray(), actualFruits);
        // Update db element
        db.put("apple", 200);
        actualFruits = fruitStorageDao.getAllFruits();
        Assertions.assertArrayEquals(db.keySet().toArray(), actualFruits);
    }
}
