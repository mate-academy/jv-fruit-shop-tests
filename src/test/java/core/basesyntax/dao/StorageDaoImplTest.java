package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageDaoImplTest {
    private StorageDao storageDao;

    @BeforeEach
    public void setUp() {
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    public void afterEachTest() {
        Storage.fruitMap.clear();
    }

    @Test
    public void addFruit_positiveTransaction_ok() {
        storageDao.addFruit("apple", 10);
        Assertions.assertEquals(10, storageDao.getFruitQuantity("apple"));
    }

    @Test
    public void getFruitQuantity_existingFruit_ok() {
        storageDao.addFruit("apple", 10);
        Assertions.assertEquals(10, storageDao.getFruitQuantity("apple"));
    }

    @Test
    public void getFruitQuantity_nonexistentFruit_ok() {
        Assertions.assertEquals(0, storageDao.getFruitQuantity("banana"));
    }

    @Test
    public void getFruitQuantity_nullFruitName_notOk() {
        Assertions.assertEquals(0, storageDao.getFruitQuantity(null));
    }

    @Test
    public void getAllFruits_ok() {
        Map<String, Integer> expectedFruitMap = new HashMap<>();
        expectedFruitMap.put("apple", 10);
        expectedFruitMap.put("orange", 5);
        storageDao.addFruit("apple", 10);
        storageDao.addFruit("orange", 5);
        Assertions.assertEquals(expectedFruitMap, storageDao.getAllFruits());
    }

    @Test
    public void addFruit_nullFruit_notOk() {
        assertThrows(IllegalArgumentException.class, () -> {
            storageDao.addFruit(null, 10);
        });
    }

    @Test
    public void addFruit_negativeTransaction_notOk() {
        assertThrows(RuntimeException.class,
                () -> storageDao.addFruit("apple", -5));
    }
}
