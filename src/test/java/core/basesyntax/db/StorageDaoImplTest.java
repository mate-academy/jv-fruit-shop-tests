package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import core.basesyntax.models.Fruit;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageDaoImplTest {
    private static StorageDao storageDao;

    @BeforeClass
    public static void beforeClass() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    public void add_newFruitIntegerPair_Ok() {
        Fruit testFruit = new Fruit("Peach");
        Integer expected = 7;
        storageDao.add(testFruit, expected);
        Integer actual = Storage.storage.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void get_existingFruitQuantity_Ok() {
        Fruit testFruit = new Fruit("Peach");
        Integer expected = 7;
        Storage.storage.put(testFruit, expected);
        Integer actual = storageDao.get(testFruit);
        assertEquals(expected, actual);
    }

    @Test
    public void get_nonExistingFruitQuantity_NotOk() {
        Fruit testFruit = new Fruit("Peach");
        Integer actual = storageDao.get(testFruit);
        assertNull(actual);
    }

    @Test
    public void getAll_getAllFruitsQuantity_Ok() {
        Storage.storage.put(new Fruit("Coconut"),1);
        Storage.storage.put(new Fruit("Orange"),1);
        Storage.storage.put(new Fruit("Kiwi"),1);
        Set<Map.Entry<Fruit, Integer>> getAllResult = storageDao.getAll();
        int expectedSize = 3;
        int actualSize = getAllResult.size();
        assertEquals(expectedSize, actualSize);
        int expectedSumOfValues = 3;
        int actualSumOfValues = getAllResult.stream().mapToInt(Map.Entry::getValue).sum();
        assertEquals(expectedSumOfValues, actualSumOfValues);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
