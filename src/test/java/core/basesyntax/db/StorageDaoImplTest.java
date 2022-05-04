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
    private static StorageDao storage;
    private static Fruit testFruit1;

    @BeforeClass
    public static void beforeClass() {
        storage = new StorageDaoImpl();
    }

    @Test
    public void add_FruitIntegerPair_Ok() {
        testFruit1 = new Fruit("Peach");
        Integer expected = 7;
        storage.add(testFruit1, expected);
        Integer actual = Storage.storage.get(testFruit1);
        assertEquals(expected, actual);
    }

    @Test
    public void get_existingFruitQuantity_Ok() {
        testFruit1 = new Fruit("Peach");
        Integer expected = 7;
        storage.add(testFruit1, expected);
        Integer actual = storage.get(testFruit1);
        assertEquals(expected, actual);
    }

    @Test
    public void get_nonExistingFruitQuantity_NotOk() {
        testFruit1 = new Fruit("Peach");
        Integer actual = storage.get(testFruit1);
        assertNull(actual);
    }

    @Test
    public void getAll_getAllFruitsQuantity_Ok() {
        Storage.storage.put(new Fruit("Coconut"),7);
        Storage.storage.put(new Fruit("Orange"),13);
        Storage.storage.put(new Fruit("Kiwi"),17);
        Set<Map.Entry<Fruit, Integer>> storageEntrySet = storage.getAll();
        Set<Map.Entry<Fruit, Integer>> getAllResult = storage.getAll();
        int expectedSize = storageEntrySet.size();
        int actualSize = getAllResult.size();
        assertEquals(expectedSize, actualSize);
        int expectedSumOfValues = storageEntrySet.stream().mapToInt(Map.Entry::getValue).sum();
        int actualSumOfValues = getAllResult.stream().mapToInt(Map.Entry::getValue).sum();
        assertEquals(expectedSumOfValues, actualSumOfValues);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
