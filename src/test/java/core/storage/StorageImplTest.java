package core.storage;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class StorageImplTest {

    private Storage storage;

    @Before
    public void setUp() {
        storage = new StorageImpl();
        storage.add("apple", 100);
        storage.add("banana", 50);
    }

    @Test
    public void storageImpl_getAllData_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("banana", 50);
        Map<String, Integer> actual = storage.getAllData();
        assertEquals(expected, actual);
    }

    @Test
    public void storageImpl_addNewFruitAndCheck_Ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 100);
        expected.put("banana", 50);
        expected.put("coconut", 10);
        storage.add("coconut", 10);
        Map<String, Integer> actual = storage.getAllData();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void storageImpl_removeFromStorageFruitLessThanZero_NotOk() {
        storage.remove("apple", -10);
    }

    @Test(expected = RuntimeException.class)
    public void storageImpl_removeFromStorageFruitWichIsNullOrMissing_NotOk() {
        storage.remove(null, 10);
        storage.remove("asdaas", 10);
    }

    @Test(expected = RuntimeException.class)
    public void storageImpl_removeFromStorageFruitMoreThanInStorage() {
        storage.remove("apple", 200);
    }

    @Test
    public void storageImpl_removeFromStorage_Ok() {
        storage.remove("apple", 50);
        Integer actual = storage.getAllData().get("apple");
        Integer expected = 50;
        assertEquals(expected, actual);
    }
}
