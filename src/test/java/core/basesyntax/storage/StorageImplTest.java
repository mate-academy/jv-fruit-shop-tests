package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        Map<String, Integer> allDataBeforeAdd = storage.getAllData();
        allDataBeforeAdd.put("coconut", 10);
        storage.add("coconut", 10);
        Map<String, Integer> allDataAfterAdd = storage.getAllData();
        assertEquals(allDataBeforeAdd, allDataAfterAdd);
    }

    @Test
    public void storageImpl_removeFromStorageFruitLessThanZero_NotOk() {
        assertThrows(RuntimeException.class,() -> storage.removeFromStorage("apple", -10));
    }

    @Test
    public void storageImpl_removeFromStorageFruitWichIsNullOrMissing_NotOk() {
        assertThrows(RuntimeException.class,() -> storage.removeFromStorage(null, 10));
        assertThrows(RuntimeException.class,() -> storage.removeFromStorage("asdaas", 10));
    }

    @Test
    public void storageImpl_removeFromStorageFruitMoreThanInStorage() {
        assertThrows(RuntimeException.class,() -> storage.removeFromStorage("apple", 200));
    }

    @Test
    public void storageImpl_removeFromStorage_Ok() {
        storage.removeFromStorage("apple", 50);
        Integer actual = storage.getAllData().get("apple");
        Integer expected = 50;
        assertEquals(expected, actual);
    }
}
